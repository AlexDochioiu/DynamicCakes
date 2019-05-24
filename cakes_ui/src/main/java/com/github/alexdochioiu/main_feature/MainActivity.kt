package com.github.alexdochioiu.main_feature

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

        swipeRefreshLayout.setOnRefreshListener {
            mainViewModel.updateCakes()
        }

        mainViewModel.cakes.observe(this, Observer { observedItems ->
            swipeRefreshLayout.isRefreshing = false
            Toast.makeText(this, getString(R.string.refreshed_cakes_toast_message), Toast.LENGTH_SHORT).show()

            cakesAdapter.replaceItemsAndNotify(observedItems)
        })

        mainViewModel.failure.observe(this, Observer { errorMessage ->
            swipeRefreshLayout.isRefreshing = false
            cakesAdapter.clearItemsAndNotify()

            AlertDialog.Builder(this)
                .setTitle(R.string.generic_error)
                .setMessage(errorMessage)
                .setPositiveButton(R.string.retry_button) { dialog, _ ->
                    dialog.dismiss()
                    swipeRefreshLayout.isRefreshing = true

                    mainViewModel.updateCakes()
                }.setNegativeButton(R.string.cancel_button) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        })

        swipeRefreshLayout.isRefreshing = true
        mainViewModel.updateCakes()
    }

    override fun onCakeSelected(cake: Cake) {
        // todo ideally we don't want (view) actions triggered from any place other than the viewmodel.
        //          there are multiple solutions to do this (observable pattern, vm implementic listener, etc)

        AlertDialog.Builder(this)
            .setMessage(cake.description)
            .setPositiveButton(R.string.ok_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
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
