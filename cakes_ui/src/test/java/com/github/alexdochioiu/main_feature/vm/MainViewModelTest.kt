package com.github.alexdochioiu.main_feature.vm

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.alexdochioiu.main_feature.R
import com.github.alexdochioiu.main_feature.vm.test_utils.TestSchedulersProvider
import com.github.alexdochioiu.main_feature_common_objects.Cake
import com.github.alexdochioiu.main_feature_repository.CakesRepository
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertSame
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException
import java.util.concurrent.CountDownLatch


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest { // todo extract a base ViewModelTest class and put it in the core module
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var appContext: Context
    @Mock
    lateinit var cakesRepository: CakesRepository

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        doReturn(ERROR_MESSAGE).`when`(appContext).getString(R.string.failed_fetch_dialog_body_no_internet)

        mainViewModel = MainViewModel(cakesRepository, TestSchedulersProvider(), appContext)
    }

    @Test(timeout = 100L)
    fun `updateCakes requests data from repository`() {
        val countDownLatch = CountDownLatch(1)

        doAnswer {
            countDownLatch.countDown()
            return@doAnswer Single.just(emptyList<Cake>())
        }.`when`(cakesRepository).getUniqueOrderedCakes()

        mainViewModel.updateCakes()
        countDownLatch.await()
    }

    @Test(timeout = 100L)
    fun `getCakes receives data from repo`() {
        val countDownLatch = CountDownLatch(1)
        val expected = listOf<Cake>()

        doReturn(Single.just(expected)).`when`(cakesRepository).getUniqueOrderedCakes()

        mainViewModel.cakes.observeForever { returned ->
            countDownLatch.countDown()
            assertSame(expected, returned)
        }

        mainViewModel.failure.observeForever {
            Assert.fail()
        }

        mainViewModel.updateCakes()
        countDownLatch.await()
    }

    @Test(timeout = 100L)
    fun `getFailure receives failure when fetching data fetching`() {
        val countDownLatch = CountDownLatch(1)

        doAnswer { Single.error<List<Cake>>(RuntimeException("")) }.`when`(cakesRepository).getUniqueOrderedCakes()

        mainViewModel.cakes.observeForever { returned ->
            Assert.fail()
        }

        mainViewModel.failure.observeForever { returned ->
            countDownLatch.countDown()
            assertSame(ERROR_MESSAGE, returned)
        }

        mainViewModel.updateCakes()
        countDownLatch.await()
    }


    companion object {
        private const val ERROR_MESSAGE = "error_message"

        @BeforeClass
        @JvmStatic
        fun setUpRxSchedulers() {
            val trampoline = Schedulers.trampoline()

            RxJavaPlugins.setInitIoSchedulerHandler { trampoline }
            RxJavaPlugins.setInitComputationSchedulerHandler { trampoline }
            RxJavaPlugins.setInitNewThreadSchedulerHandler { trampoline }
            RxJavaPlugins.setInitSingleSchedulerHandler { trampoline }
            //RxAndroidPlugins.setInitMainThreadSchedulerHandler { trampoline }
        }
    }
}