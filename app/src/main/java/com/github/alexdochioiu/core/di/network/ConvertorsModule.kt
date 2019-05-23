package com.github.alexdochioiu.core.di.network

import com.github.alexdochioiu.core.di.CoreScope
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Alex Dochioiu on 2019-05-22
 */
@Module
internal class ConvertorsModule {
    @Provides
    @CoreScope
    internal fun converterFactory(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi)

    @Provides
    @CoreScope
    internal fun moshi(): Moshi = Moshi.Builder().build()
}