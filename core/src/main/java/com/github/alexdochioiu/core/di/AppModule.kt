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
import com.github.alexdochioiu.core.rxjava.SchedulersProvider
import com.github.alexdochioiu.core.rxjava.SchedulersProviderImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Alex Dochioiu on 2019-05-22
 */
@Module
internal interface AppModule {

    @Binds
    fun application(newsApplication: CoreApplication): Application

    @Binds
    @AppContext
    fun context(newsApplication: CoreApplication): Context

    @Binds
    fun schedulersProvider(schedulersProviderImpl: SchedulersProviderImpl): SchedulersProvider
}