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
import dagger.BindsInstance
import dagger.Component
import retrofit2.Converter

/**
 * Created by Alex Dochioiu on 2019-05-15
 */
@Component(modules = [RetrofitServiceModule::class])
@Feature_NetworkingPersistenceScope
abstract class CakesNetworkComponent {

    //region Retrofit Services
    abstract fun cakesService(): CakesService
    //endregion

    @Component.Factory
    internal abstract class Factory {
        internal abstract fun create(
            @BindsInstance retrofitFactory: RetrofitFactory,
            @BindsInstance converterFactory: Converter.Factory
        ): CakesNetworkComponent
    }

    companion object {
        fun create(
            retrofitFactory: RetrofitFactory,
            converterFactory: Converter.Factory
        ): CakesNetworkComponent =
            DaggerCakesNetworkComponent.factory().create(retrofitFactory, converterFactory)
    }
}