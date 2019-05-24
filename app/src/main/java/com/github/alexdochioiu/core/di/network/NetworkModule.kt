package com.github.alexdochioiu.core.di.network

import android.content.Context
import android.util.Log
import com.github.alexdochioiu.core.di.AppContext
import com.github.alexdochioiu.core.di.CoreScope
import com.jakewharton.picasso.OkHttp3Downloader
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

/**
 * Created by Alex Dochioiu on 2019-05-22
 */
@Module
internal class NetworkModule {

    @Provides
    @CoreScope
    internal fun loggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message -> Log.v("okhttp3", message) }
            .apply { level = HttpLoggingInterceptor.Level.BASIC }

    /*@Provides todo add it back if you want to cache the network response. I turned it off to avoid retrieving a cached response when checking for the no internet connection error
    @CoreScope
    internal fun cache(cacheFile: File): Cache = Cache(cacheFile, 10 * 1000 * 1000) //10MB Cache*/

    @Provides
    @CoreScope
    internal fun cacheFile(@AppContext context: Context): File = File(context.cacheDir, "okhttp_cache")

    @Provides
    @CoreScope
    internal fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
        //cache: Cache
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            //.cache(cache)
            .build()

    @Provides
    @CoreScope
    internal fun picassoDownloader(
        okHttpClient: OkHttpClient
    ): OkHttp3Downloader = OkHttp3Downloader(okHttpClient)

}