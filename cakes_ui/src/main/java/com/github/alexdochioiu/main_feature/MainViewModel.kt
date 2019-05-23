package com.github.alexdochioiu.main_feature

import androidx.lifecycle.ViewModel
import com.github.alexdochioiu.main_feature_common_objects.Cake
import com.github.alexdochioiu.main_feature_repository.CakesRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel constructor(private val cakesRepository: CakesRepository) : ViewModel() {
    private var cakes: List<Cake>? = null

    fun getCakes(): Single<List<Cake>> = when (cakes) {
        null -> cakesRepository.getAllCakes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { cakes = it }
        else -> Single.just(cakes)
    }

}