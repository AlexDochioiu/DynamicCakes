package com.github.alexdochioiu.main_feature.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.github.alexdochioiu.core.mvvm.SingleLiveEvent
import com.github.alexdochioiu.main_feature_common_objects.Cake
import com.github.alexdochioiu.main_feature_repository.CakesRepository
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel constructor(private val cakesRepository: CakesRepository) : ViewModel() {

    private var cakesDisposable: Disposable? = null

    private val _cakes: MediatorLiveData<List<Cake>> = MediatorLiveData()
    val cakes : LiveData<List<Cake>>
        get() = _cakes

    private val _failure: SingleLiveEvent<String> = SingleLiveEvent()
    val failure : LiveData<String>
        get() = _failure // MAX ONE OBSERVER AT ONCE DUE TO [SingleLiveEvent] LIMITATION

    fun updateCakes() {
        val disposable = cakesDisposable
        if (disposable == null || !disposable.isDisposed) {
            //do not restart the call unless the previous one finished

            cakesDisposable = cakesRepository.getUniqueOrderedCakes()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { repoCakes -> _cakes.postValue(repoCakes) },
                    { _failure.postValue("Something went wrong") }) // todo this can be handled by checking the type of failure
        }
    }

}