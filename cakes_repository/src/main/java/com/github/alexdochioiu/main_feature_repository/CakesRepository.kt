package com.github.alexdochioiu.main_feature_repository

import com.github.alexdochioiu.core.di.Feature_RepositoryScope
import com.github.alexdochioiu.main_feature_common_objects.Cake
import com.github.alexdochioiu.main_feature_networking.retrofit.CakesService
import io.reactivex.Single
import io.reactivex.rxkotlin.flatMapIterable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * This should be linking the networking and persistence together. The internal constructor ensures we do not create
 * this object outside this module
 */
@Feature_RepositoryScope
class CakesRepository @Inject internal constructor(val cakesService: CakesService){

    // todo maybe first try and get them from a db if a persistence layer existed
    fun getUniqueOrderedCakes() : Single<List<Cake>> =
        cakesService.getAllCakes()
            .toObservable()
            .flatMapIterable()
            .distinct()
            .toSortedList { first, second -> first.title.compareTo(second.title) }
}