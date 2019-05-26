package com.github.alexdochioiu.core

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.github.alexdochioiu.core.di.CoreComponent
import com.github.alexdochioiu.core.di.DaggerCoreComponent
import com.github.alexdochioiu.core.navigation.DynamicFeature
import com.github.alexdochioiu.core.navigation.DynamicNavigationManager
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Alex Dochioiu on 2019-05-22
 */
class CoreApplication : Application() {

    @Inject
    internal lateinit var dynamicNavigationManager: DynamicNavigationManager

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)

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