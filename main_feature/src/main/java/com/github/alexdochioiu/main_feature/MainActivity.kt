package com.github.alexdochioiu.main_feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.alexdochioiu.core.appComponent
import com.github.alexdochioiu.main_feature.di.DaggerFeatureComponent
import com.github.alexdochioiu.main_feature.di.FeatureComponent
import com.github.alexdochioiu.main_feature_networking.di.DaggerCakesNetworkComponent
import com.github.alexdochioiu.main_feature_repository.CakesRepository
import com.github.alexdochioiu.main_feature_repository.di.DaggerCakesRepositoryComponent
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var cakesRepository: CakesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inject()

        Timber.e(cakesRepository.toString())
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
