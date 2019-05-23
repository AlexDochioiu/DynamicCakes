package com.github.alexdochioiu.main_feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.alexdochioiu.core.appComponent
import com.github.alexdochioiu.main_feature.di.DaggerFeatureComponent
import com.github.alexdochioiu.main_feature.di.FeatureComponent
import com.github.alexdochioiu.main_feature.vm.MainViewModelFactory
import com.github.alexdochioiu.main_feature_networking.di.DaggerCakesNetworkComponent
import com.github.alexdochioiu.main_feature_repository.CakesRepository
import com.github.alexdochioiu.main_feature_repository.di.DaggerCakesRepositoryComponent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var disposables: CompositeDisposable //todo this should probably go into some BaseActivity class (part of app module)
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposables = CompositeDisposable()
        inject()

        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)

        disposables.add(mainViewModel.getCakesObservable().subscribe { Timber.d(it.toString()) } )
        mainViewModel.updateCakes()
    }

    override fun onDestroy() {
        super.onDestroy()

        disposables.dispose()
    }

    private val featureComponent: FeatureComponent by lazy {
        val cakesNetworkComponent = DaggerCakesNetworkComponent
            .builder()
            .coreComponent(this.appComponent())
            .build()

        val cakesRepositoryComponent = DaggerCakesRepositoryComponent
            .builder()
            .cakesNetworkComponent(cakesNetworkComponent)
            .build()

        DaggerFeatureComponent
            .builder()
            .cakesRepositoryComponent(cakesRepositoryComponent)
            .build()
    }

    companion object {
        @JvmStatic
        fun featureComponent(activity: MainActivity) =
            activity.featureComponent

        fun MainActivity.inject() {
            featureComponent(this).inject(this)
        }
    }
}
