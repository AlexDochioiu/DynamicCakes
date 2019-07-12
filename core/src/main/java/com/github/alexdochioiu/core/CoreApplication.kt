/*
 * Copyright 2019 Alexandru Iustin Dochioiu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.alexdochioiu.core

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.github.alexdochioiu.core.di.CoreComponent
import com.github.alexdochioiu.core.di.DaggerCoreComponent
import com.github.alexdochioiu.core.navigation.DynamicFeature
import com.github.alexdochioiu.core.navigation.DynamicNavigationManager
import timber.log.Timber
import java.io.InputStream
import javax.inject.Inject

/**
 * Created by Alex Dochioiu on 2019-05-22
 */
class CoreApplication : Application() {

    @Inject
    internal lateinit var dynamicNavigationManager: DynamicNavigationManager

    @Inject
    internal lateinit var glideDownloader: OkHttpUrlLoader.Factory

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)

        Glide.get(this).apply {
            registry.replace(GlideUrl::class.java, InputStream::class.java, glideDownloader)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // todo I guess a custom tree can go here and the non-fatal errors can be reported to some bugs/errors tracker
        }
    }

    private val appComponent: CoreComponent by lazy {
        DaggerCoreComponent
            .factory()
            .create(this)
    }

    companion object {
        @JvmStatic
        fun appComponent(context: Context) =
            (context.applicationContext as CoreApplication).appComponent
    }
}

internal fun EntryActivity.navigateToFeature(feature: DynamicFeature) =
    this.getCoreApplication().dynamicNavigationManager.navigateToFeature(this, feature)

fun Activity.getCoreApplication() = this.application as CoreApplication
fun FragmentActivity.getCoreApplication() = this.application as CoreApplication

/**
 * Those two extensions should be *the only way* to fetch the core component
 * todo should think of a way to fully block people from creating a core component inside a different android module
 */
fun Activity.coreComponent() = CoreApplication.appComponent(this)
fun FragmentActivity.coreComponent() = CoreApplication.appComponent(this)