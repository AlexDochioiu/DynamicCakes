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

package com.github.alexdochioiu.main_feature.di

import com.github.alexdochioiu.core.di.Feature_UiScope
import com.github.alexdochioiu.core.di.InjectableComponent
import com.github.alexdochioiu.main_feature.MainFragment
import com.github.alexdochioiu.main_feature.adapter.CakesAdapter
import com.github.alexdochioiu.main_feature_repository.di.CakesRepositoryComponent
import dagger.BindsInstance
import dagger.Component

@Feature_UiScope
@Component(dependencies = [CakesRepositoryComponent::class])
interface FeatureComponent : InjectableComponent<MainFragment> {

    @Component.Factory
    interface Factory {
        fun create(
            component: CakesRepositoryComponent,
            @BindsInstance listener: CakesAdapter.CakesListener
        ): FeatureComponent
    }
}