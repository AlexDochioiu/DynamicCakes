package com.github.alexdochioiu.main_feature_repository.di

import com.github.alexdochioiu.core.di.CoreComponent
import com.github.alexdochioiu.core.di.Feature_RepositoryScope
import com.github.alexdochioiu.main_feature_networking.di.CakesNetworkComponent
import com.github.alexdochioiu.main_feature_networking.di.DaggerCakesNetworkComponent
import com.github.alexdochioiu.main_feature_networking.retrofit.CakesService
import dagger.Module
import dagger.Provides

@Module
internal class NetworkingBridgeModule {

    /**
     * I know this looks a bit weird but it allows me to keep using implementation instead of api for the networking module
     * dependency. It also maintains all the compile-time safety and there's no performance impact to it
     */
    @Feature_RepositoryScope
    @Provides
    fun networkingComponent(coreComponent: CoreComponent): CakesNetworkComponent =
        DaggerCakesNetworkComponent.builder()
            .coreComponent(coreComponent)
            .build()

    @Feature_RepositoryScope
    @Provides
    fun cakesService(cakesNetworkComponent: CakesNetworkComponent): CakesService =
            cakesNetworkComponent.cakesService()
}