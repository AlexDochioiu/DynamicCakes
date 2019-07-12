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

package com.github.alexdochioiu.main_feature_repository

import com.github.alexdochioiu.core.di.Feature_RepositoryScope
import com.github.alexdochioiu.main_feature_common_objects.Cake
import com.github.alexdochioiu.main_feature_networking.retrofit.CakesService
import io.reactivex.Single
import io.reactivex.rxkotlin.flatMapIterable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * This should be linking the networking and persistence together. The internal constructor ensures we do not create
 * this object outside this module
 */
@Feature_RepositoryScope
class CakesRepository @Inject internal constructor(private val cakesService: CakesService){

    // todo maybe first try and get them from a db if a persistence layer existed
    fun getUniqueOrderedCakes() : Single<List<Cake>> =
        cakesService.getAllCakes()
            .toObservable()
            .flatMapIterable()
            .distinct()
            .toSortedList { first, second -> first.title.compareTo(second.title) }
}