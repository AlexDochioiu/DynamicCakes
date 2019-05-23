package com.github.alexdochioiu.main_feature

import androidx.lifecycle.ViewModel
import com.github.alexdochioiu.main_feature_common_objects.Cake
import com.github.alexdochioiu.main_feature_repository.CakesRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class MainViewModel constructor(private val cakesRepository: CakesRepository) : ViewModel() {
    private val cakesSubject: BehaviorSubject<List<Cake>> = BehaviorSubject.create()
    var cakesDisposable: Disposable? = null

    fun updateCakes() {
        val disposable = cakesDisposable
        if (disposable == null || !disposable.isDisposed) {
            //do not restart the call unless the previous one finished

            cakesDisposable = cakesRepository.getAllCakes()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { cakes -> cakesSubject.onNext(cakes) },
                    { error -> TODO() })
        }
    }

    fun getCakesObservable() : Observable<List<Cake>> = cakesSubject.observeOn(AndroidSchedulers.mainThread())

}