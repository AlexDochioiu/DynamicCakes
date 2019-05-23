package com.github.alexdochioiu.main_feature_repository.di

import android.app.Application
import android.content.Context
import com.github.alexdochioiu.core.CoreApplication
import com.github.alexdochioiu.core.di.AppContext
import com.github.alexdochioiu.core.di.Feature_RepositoryScope
import com.github.alexdochioiu.main_feature_networking.di.CakesNetworkComponent
import com.github.alexdochioiu.main_feature_repository.CakesRepository
import dagger.Component

@Feature_RepositoryScope
@Component(dependencies = [CakesNetworkComponent::class]) //TODO there would be the persistence dependency too
interface CakesRepositoryComponent {

    //region Context - Forwarding them down to the ui module
    fun newsApplication(): CoreApplication

    fun application(): Application

    @AppContext
    fun appContext(): Context
    //endregion

    //region Repositories
    fun cakesRepo(): CakesRepository
    //endregion

}