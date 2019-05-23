package com.github.alexdochioiu.main_feature_networking.retrofit

import com.github.alexdochioiu.main_feature_common_objects.Cake
import io.reactivex.Single
import retrofit2.http.GET

interface CakesService {
    @GET("t-reed/739df99e9d96700f17604a3971e701fa/raw/1d4dd9c5a0ec758ff5ae92b7b13fe4d57d34e1dc/waracle_cake-android-client")
    fun getAllCakes() : Single<List<Cake>>
}