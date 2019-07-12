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

private const val PACKAGE_BASE = "com.github.alexdochioiu"
private const val PACKAGE_NAME = "$PACKAGE_BASE.core"

object Features {
    object Main : DynamicFeature() {
        override val entryActivityName: String = "$PACKAGE_BASE.main_feature.MainActivity"
    }

    object Dashboard : DynamicFeature() {
        override val entryActivityName: String = "$PACKAGE_BASE.dashboard_ui.DashboardActivity"
    }
}

abstract class DynamicFeature internal constructor(){
    val entryPackageName: String = PACKAGE_NAME
    abstract val entryActivityName: String
}