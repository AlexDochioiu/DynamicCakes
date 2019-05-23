package com.github.alexdochioiu.main_feature_networking.di

import com.github.alexdochioiu.core.di.Feature_NetworkingPersistenceScope
import com.github.alexdochioiu.core.network.RetrofitFactory
import com.github.alexdochioiu.main_feature_networking.baseApiUrl
import com.github.alexdochioiu.main_feature_networking.retrofit.CakesService
import dagger.Module
import dagger.Provides
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Created by Alex Dochioiu on 2019-05-17
 */
@Module
internal class RetrofitServiceModule {
    @Provides
    @Feature_NetworkingPersistenceScope
    internal fun callAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.createAsync()

    @Provides
    @Feature_NetworkingPersistenceScope
    internal fun searchService(retrofit: Retrofit): CakesService = retrofit.create(CakesService::class.java)

    @Provides
    @Feature_NetworkingPersistenceScope
    internal fun retrofit(
        retrofitFactory: RetrofitFactory,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory
    ): Retrofit = retrofitFactory.makeInstance(baseApiUrl, converterFactory, callAdapterFactory)
}