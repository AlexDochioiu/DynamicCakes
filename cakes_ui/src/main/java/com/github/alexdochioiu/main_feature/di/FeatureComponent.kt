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