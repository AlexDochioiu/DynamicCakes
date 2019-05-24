package com.github.alexdochioiu.main_feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.alexdochioiu.core.appComponent
import com.github.alexdochioiu.main_feature.di.DaggerFeatureComponent
import com.github.alexdochioiu.main_feature.di.FeatureComponent
import com.github.alexdochioiu.main_feature.vm.MainViewModelFactory
import com.github.alexdochioiu.main_feature_networking.di.DaggerCakesNetworkComponent
import com.github.alexdochioiu.main_feature_repository.CakesRepository
import com.github.alexdochioiu.main_feature_repository.di.DaggerCakesRepositoryComponent
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inject()

        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)

        mainViewModel.cakes.observe(this, Observer {
            Timber.d(it.toString())
        })

        mainViewModel.failure.observe(this, Observer {
            Timber.e(it) //TODO popup
        })

        mainViewModel.updateCakes()
    }

    private val featureComponent: FeatureComponent by lazy {
        val cakesNetworkComponent = DaggerCakesNetworkComponent
            .builder()
            .coreComponent(this.appComponent())
            .build()

        val cakesRepositoryComponent = DaggerCakesRepositoryComponent
            .builder()
            .cakesNetworkComponent(cakesNetworkComponent)
            .build()

        DaggerFeatureComponent
            .builder()
            .cakesRepositoryComponent(cakesRepositoryComponent)
            .build()
    }

    companion object {
        @JvmStatic
        fun featureComponent(activity: MainActivity) =
            activity.featureComponent

        fun MainActivity.inject() {
            featureComponent(this).inject(this)
        }
    }
}
