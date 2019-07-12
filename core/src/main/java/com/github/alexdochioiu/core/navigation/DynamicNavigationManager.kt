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

package com.github.alexdochioiu.core.navigation

import android.app.Activity
import android.content.Intent
import com.github.alexdochioiu.core.di.CoreScope
import javax.inject.Inject

@CoreScope
class DynamicNavigationManager @Inject internal constructor() {

    //TODO this class is very basic at the moment but if we start having features provided on-demand and features which
    //TODO    require args, considerably more work should be done
    fun navigateToFeature(activity: Activity, feature: DynamicFeature, flags: Int = 0) {
        activity.startActivity(feature.makeNewIntent(flags))
    }

}

private fun DynamicFeature.makeNewIntent(flags: Int): Intent =
    Intent(Intent.ACTION_VIEW).setClassName(this.entryPackageName, this.entryActivityName)
        .apply { addFlags(flags) }