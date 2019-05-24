package com.github.alexdochioiu.main_feature.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.alexdochioiu.core.di.Feature_UiScope
import com.github.alexdochioiu.main_feature_repository.CakesRepository
import javax.inject.Inject

@Feature_UiScope
class MainViewModelFactory @Inject internal constructor(private val cakesRepository: CakesRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass != MainViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

        return MainViewModel(cakesRepository) as T
    }
}