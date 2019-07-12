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

@file:Suppress("ClassName")

package com.github.alexdochioiu.core.di

/**
 * Created by Alex Dochioiu on 2019-05-22
 */
import javax.inject.Scope

@Retention(AnnotationRetention.SOURCE)
@Scope
annotation class CoreScope

/**
 * This scope is used for both networking and persistence (if any) layers so the [dagger.Component] for [Feature_RepositoryScope]
 * can depend on both components
 * _Note:_ A dagger component can depend on more than one component *only* if all of them have the same scope!
 */
@Retention(AnnotationRetention.SOURCE)
@Scope //
annotation class Feature_NetworkingPersistenceScope

@Retention(AnnotationRetention.SOURCE)
@Scope //
annotation class Feature_RepositoryScope

@Retention(AnnotationRetention.SOURCE)
@Scope
annotation class Feature_UiScope