package com.github.alexdochioiu.main_feature_repository.di

import android.app.Application
import android.content.Context
import com.github.alexdochioiu.core.CoreApplication
import com.github.alexdochioiu.core.di.AppContext
import com.github.alexdochioiu.core.di.CoreComponent
import com.github.alexdochioiu.core.di.DaggerCoreComponent
import com.github.alexdochioiu.core.di.Feature_RepositoryScope
import com.github.alexdochioiu.core.rxjava.SchedulersProvider
import com.github.alexdochioiu.main_feature_repository.CakesRepository
import com.jakewharton.picasso.OkHttp3Downloader
import dagger.Component

@Feature_RepositoryScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [NetworkingBridgeModule::class] //todo persistence layer too if it exists
)
interface CakesRepositoryComponent {

    //region Context - Forwarding them down to the ui module
    fun newsApplication(): CoreApplication

    fun application(): Application

    @AppContext
    fun appContext(): Context

    fun schedulersProvider(): SchedulersProvider
    //endregion

    //region Network
    fun picassoDownloader(): OkHttp3Downloader
    //endregion

    //region Repositories
    fun cakesRepo(): CakesRepository
    //endregion
}