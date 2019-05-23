package com.github.alexdochioiu.main_feature_networking.di

import android.app.Application
import android.content.Context
import com.github.alexdochioiu.core.CoreApplication
import com.github.alexdochioiu.core.di.AppContext
import com.github.alexdochioiu.core.di.CoreComponent
import com.github.alexdochioiu.core.di.Feature_NetworkingPersistenceScope
import com.github.alexdochioiu.main_feature_networking.retrofit.CakesService
import dagger.Component

/**
 * Created by Alex Dochioiu on 2019-05-15
 */
@Component(dependencies = [CoreComponent::class], modules = [RetrofitServiceModule::class])
@Feature_NetworkingPersistenceScope
interface CakesNetworkComponent {

    //region Context
    fun newsApplication(): CoreApplication

    fun application(): Application

    @AppContext
    fun appContext(): Context
    //endregion

    //region Retrofit Services
    fun cakesService(): CakesService
    //endregion

}