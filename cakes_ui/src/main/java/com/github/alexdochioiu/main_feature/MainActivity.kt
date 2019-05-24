package com.github.alexdochioiu.main_feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.alexdochioiu.core.appComponent
import com.github.alexdochioiu.main_feature.adapter.CakesAdapter
import com.github.alexdochioiu.main_feature.di.DaggerFeatureComponent
import com.github.alexdochioiu.main_feature.di.FeatureComponent
import com.github.alexdochioiu.main_feature.vm.MainViewModel
import com.github.alexdochioiu.main_feature.vm.MainViewModelFactory
import com.github.alexdochioiu.main_feature_common_objects.Cake
import com.github.alexdochioiu.main_feature_networking.di.DaggerCakesNetworkComponent
import com.github.alexdochioiu.main_feature_repository.di.DaggerCakesRepositoryComponent
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CakesAdapter.CakesListener {
    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    @Inject
    lateinit var cakesAdapter: CakesAdapter

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inject()

        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)

        rvCakes.layoutManager = LinearLayoutManager(this)
        rvCakes.adapter = cakesAdapter

        mainViewModel.cakes.observe(this, Observer { observedItems ->
            cakesAdapter.replaceItemsAndNotify(observedItems)
        })

        mainViewModel.failure.observe(this, Observer {
            cakesAdapter.clearItemsAndNotify()
            Timber.e(it) //TODO popup
        })

        mainViewModel.updateCakes()
    }

    override fun onCakeSelected(cake: Cake) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
            .factory()
            .create(cakesRepositoryComponent, this)
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
