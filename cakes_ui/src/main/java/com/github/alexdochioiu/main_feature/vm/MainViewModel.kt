package com.github.alexdochioiu.main_feature.vm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.alexdochioiu.core.mvvm.SingleLiveEvent
import com.github.alexdochioiu.core.rxjava.SchedulersProvider
import com.github.alexdochioiu.main_feature.R
import com.github.alexdochioiu.main_feature_common_objects.Cake
import com.github.alexdochioiu.main_feature_repository.CakesRepository
import io.reactivex.disposables.Disposable

class MainViewModel constructor(
    private val cakesRepository: CakesRepository,
    private val schedulersProvider: SchedulersProvider,
    private val appContext: Context // todo wrap this in some ResourceProvider class
) : ViewModel() {

    private val lockObject = Object()
    private var cakesDisposable: Disposable? = null

    private val _cakes: MutableLiveData<List<Cake>> = MutableLiveData()
    val cakes: LiveData<List<Cake>>
        get() = _cakes

    private val _failure: SingleLiveEvent<String> = SingleLiveEvent()
    val failure: LiveData<String>
        get() = _failure // MAX ONE OBSERVER AT ONCE DUE TO [SingleLiveEvent] LIMITATION

    /**
     * Used by the view to request the [List] of [Cake] to be updated. The response will be published using [LiveData]
     * to [cakes] if successful. If the request fails, the error message should be intercepted using [failure]
     */
    fun updateCakes() {
        synchronized(lockObject) {
            val disposable = cakesDisposable

            if (disposable == null || disposable.isDisposed) {
                //do not restart the call unless the previous one finished

                cakesDisposable = cakesRepository.getUniqueOrderedCakes()
                    .subscribeOn(schedulersProvider.getIoScheduler())
                    .subscribe(
                        { repoCakes -> _cakes.postValue(repoCakes) },
                        {
                            _failure.postValue(appContext.getString(R.string.failed_fetch_dialog_body_no_internet))
                        })
            }
        }
    }

}