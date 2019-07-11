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