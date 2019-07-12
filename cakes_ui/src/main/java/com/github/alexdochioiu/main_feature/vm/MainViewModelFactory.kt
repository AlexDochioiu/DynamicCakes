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

package com.github.alexdochioiu.main_feature.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.alexdochioiu.core.di.AppContext
import com.github.alexdochioiu.core.di.Feature_UiScope
import com.github.alexdochioiu.core.rxjava.SchedulersProvider
import com.github.alexdochioiu.main_feature_repository.CakesRepository
import javax.inject.Inject

@Feature_UiScope
class MainViewModelFactory @Inject internal constructor(
    private val cakesRepository: CakesRepository,
    private val schedulersProvider: SchedulersProvider,
    @AppContext private val appContext: Context) :
    ViewModelProvider.Factory {

    /**
     * Used to create the instance of [MainViewModel]
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass != MainViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

        return MainViewModel(cakesRepository, schedulersProvider, appContext) as T
    }
}