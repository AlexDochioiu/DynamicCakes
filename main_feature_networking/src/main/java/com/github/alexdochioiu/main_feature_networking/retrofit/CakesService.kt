package com.github.alexdochioiu.main_feature_networking.retrofit

import io.reactivex.Single
import retrofit2.http.GET

interface CakesService {
    @GET
    fun getCakes() : Single<Any> //TODO
}