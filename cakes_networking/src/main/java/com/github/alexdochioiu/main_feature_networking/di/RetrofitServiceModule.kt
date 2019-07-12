/*
 * Copyright 2019 Alexandru Iustin Dochioiu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.alexdochioiu.main_feature_networking.di

import com.github.alexdochioiu.core.di.Feature_NetworkingPersistenceScope
import com.github.alexdochioiu.core.network.RetrofitFactory
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

    companion object {
        private const val baseApiUrl = "https://gist.githubusercontent.com"
    }
}