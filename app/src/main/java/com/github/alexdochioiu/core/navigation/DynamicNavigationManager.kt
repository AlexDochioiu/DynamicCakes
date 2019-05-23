package com.github.alexdochioiu.core.navigation

import android.app.Activity
import android.content.Intent
import com.github.alexdochioiu.core.di.CoreScope
import javax.inject.Inject

@CoreScope
class DynamicNavigationManager @Inject internal constructor(){

    //TODO this class is very basic at the moment but if we start having features provided on-demand and features which
    //TODO    require args, considerably more work should be done
    fun navigateToFeature(activity: Activity, feature: DynamicFeature) {
        activity.startActivity(feature.makeNewIntent())
    }

}

private fun DynamicFeature.makeNewIntent(): Intent =
    Intent(Intent.ACTION_VIEW).setClassName(this.entryPackageName, this.entryActivityName)