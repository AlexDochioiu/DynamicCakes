package com.github.alexdochioiu.core.di.network

import android.content.Context
import android.util.Log
import com.github.alexdochioiu.core.di.AppContext
import com.github.alexdochioiu.core.di.CoreScope
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

/**
 * Created by Alex Dochioiu on 2019-05-22
 */
@Module
class NetworkModule {

    @Provides
    @CoreScope
    internal fun loggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message -> Log.v("okhttp3", message) }
            .apply { level = HttpLoggingInterceptor.Level.BASIC }

    @Provides
    @CoreScope
    internal fun cache(cacheFile: File): Cache = Cache(cacheFile, 10 * 1000 * 1000) //10MB Cache

    @Provides
    @CoreScope
    internal fun cacheFile(@AppContext context: Context): File = File(context.cacheDir, "okhttp_cache")

    @Provides
    @CoreScope
    internal fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        cache: Cache
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .build()

}