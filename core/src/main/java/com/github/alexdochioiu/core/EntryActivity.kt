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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.alexdochioiu.core.navigation.Features

internal class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO this can be a splash activity

        //approach 1 : In my view it is better in this particular case since we do not need to inflate a layout
        //                 and it also allows us to better manage the flow between dynamic features provided on demand
        navigateToFeature(Features.Dashboard)

    }
}
