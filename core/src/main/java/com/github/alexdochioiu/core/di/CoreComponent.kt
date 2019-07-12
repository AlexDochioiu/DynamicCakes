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

package com.github.alexdochioiu.core.di

import android.app.Application
import android.content.Context
import com.github.alexdochioiu.core.CoreApplication
import com.github.alexdochioiu.core.di.network.ConvertorsModule
import com.github.alexdochioiu.core.di.network.NetworkModule
import com.github.alexdochioiu.core.network.RetrofitFactory
import com.github.alexdochioiu.core.rxjava.SchedulersProvider
import com.squareup.moshi.Moshi
import dagger.BindsInstance
import dagger.Component
import retrofit2.Converter

/**
 * Created by Alex Dochioiu on 2019-05-22
 */
@CoreScope
@Component(modules = [AppModule::class, NetworkModule::class, ConvertorsModule::class])
interface CoreComponent : InjectableComponent<CoreApplication> {

    // Component.Builder can be used for older dagger2 versions but this provides compile-time safety (which the builder does not)
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: CoreApplication
        ): CoreComponent
    }

    //region Context
    fun newsApplication(): CoreApplication

    fun application(): Application

    @AppContext
    fun appContext(): Context

    fun schedulersProvider(): SchedulersProvider
    //endregion


    //region Network
    fun retrofitFactory(): RetrofitFactory
    //endregion

    //region Converters
    fun moshi(): Moshi

    fun converterFactory(): Converter.Factory
    //endregion
}