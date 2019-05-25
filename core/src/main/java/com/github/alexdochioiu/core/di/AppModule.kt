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