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

package com.github.alexdochioiu.main_feature_repository.di

import com.github.alexdochioiu.core.di.Feature_RepositoryScope
import com.github.alexdochioiu.core.network.RetrofitFactory
import com.github.alexdochioiu.main_feature_networking.di.CakesNetworkComponent
import com.github.alexdochioiu.main_feature_networking.retrofit.CakesService
import dagger.Module
import dagger.Provides
import retrofit2.Converter

@Module
internal class NetworkingBridgeModule {

    /**
     * I know this looks a bit weird but it allows me to keep using implementation instead of api for the networking module
     * dependency. It also maintains all the compile-time safety and there's no performance impact to it
     */
    @Feature_RepositoryScope
    @Provides
    fun networkingComponent(retrofitFactory: RetrofitFactory, converterFactory: Converter.Factory): CakesNetworkComponent =
        CakesNetworkComponent.create(retrofitFactory, converterFactory)

    @Feature_RepositoryScope
    @Provides
    fun cakesService(cakesNetworkComponent: CakesNetworkComponent): CakesService =
            cakesNetworkComponent.cakesService()
}