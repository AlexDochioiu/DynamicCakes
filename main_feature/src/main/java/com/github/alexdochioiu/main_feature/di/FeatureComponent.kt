package com.github.alexdochioiu.main_feature.di

import com.github.alexdochioiu.core.di.Feature_UiScope
import com.github.alexdochioiu.core.di.InjectableComponent
import com.github.alexdochioiu.main_feature.MainActivity
import com.github.alexdochioiu.main_feature_repository.di.CakesRepositoryComponent
import dagger.Component

@Feature_UiScope
@Component(dependencies = [CakesRepositoryComponent::class])
interface FeatureComponent : InjectableComponent<MainActivity> {
}