package com.github.alexdochioiu.core.di

import android.app.Application
import android.content.Context
import com.github.alexdochioiu.core.CoreApplication
import dagger.Binds
import dagger.Module

/**
 * Created by Alex Dochioiu on 2019-05-22
 */
@Module
interface AppModule {

    @Binds
    fun application(newsApplication: CoreApplication): Application

    @Binds
    @AppContext
    fun context(newsApplication: CoreApplication): Context
}