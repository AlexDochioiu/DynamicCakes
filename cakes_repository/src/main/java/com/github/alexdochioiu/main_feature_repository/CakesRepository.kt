package com.github.alexdochioiu.main_feature_repository

import com.github.alexdochioiu.core.di.Feature_RepositoryScope
import com.github.alexdochioiu.main_feature_networking.retrofit.CakesService
import javax.inject.Inject

/**
 * This should be linking the networking and persistence together. The internal constructor ensures we do not create
 * this object outside this module
 */
@Feature_RepositoryScope
class CakesRepository @Inject internal constructor(val cakesService: CakesService){

    // todo maybe first try and get them from a db if a persistence layer existed
    fun getAllCakes() = cakesService.getAllCakes()
}