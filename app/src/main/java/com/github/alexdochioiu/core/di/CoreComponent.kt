package com.github.alexdochioiu.core.di

import android.app.Application
import android.content.Context
import com.github.alexdochioiu.core.CoreApplication
import com.github.alexdochioiu.core.di.network.ConvertorsModule
import com.github.alexdochioiu.core.di.network.NetworkModule
import com.github.alexdochioiu.core.network.RetrofitFactory
import com.jakewharton.picasso.OkHttp3Downloader
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
    //endregion


    //region Network
    fun retrofitFactory(): RetrofitFactory

    fun picassoDownloader(): OkHttp3Downloader
    //endregion

    //region Converters
    fun moshi(): Moshi

    fun converterFactory(): Converter.Factory
    //endregion
}