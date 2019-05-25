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