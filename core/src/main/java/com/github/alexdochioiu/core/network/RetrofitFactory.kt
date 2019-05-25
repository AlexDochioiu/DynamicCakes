package com.github.alexdochioiu.core.network

import com.github.alexdochioiu.core.di.CoreScope
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Alex Dochioiu on 2019-05-17
 */
@CoreScope
class RetrofitFactory @Inject internal constructor(private val okHttpClient: OkHttpClient) {

    fun makeInstance(
        baseUrl: String,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory
    ): Retrofit =
        Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .baseUrl(baseUrl)
            .build()
}