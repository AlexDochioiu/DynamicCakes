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