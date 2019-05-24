package com.github.alexdochioiu.main_feature.di

import android.content.Context
import com.github.alexdochioiu.core.di.AppContext
import com.github.alexdochioiu.core.di.Feature_UiScope
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class PicassoModule {

    @Provides
    @Feature_UiScope
    fun picasso(
        okHttp3Downloader: OkHttp3Downloader,
        @AppContext context: Context
    ): Picasso = Picasso.Builder(context).downloader(okHttp3Downloader).build()
}