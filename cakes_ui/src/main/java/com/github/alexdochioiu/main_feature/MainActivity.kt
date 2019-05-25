package com.github.alexdochioiu.main_feature

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.alexdochioiu.core.CoreApplication
import com.github.alexdochioiu.main_feature.adapter.CakesAdapter
import com.github.alexdochioiu.main_feature.di.DaggerFeatureComponent
import com.github.alexdochioiu.main_feature.di.FeatureComponent
import com.github.alexdochioiu.core.ui.CustomDividerItemDecoration
import com.github.alexdochioiu.main_feature.vm.MainViewModel
import com.github.alexdochioiu.main_feature.vm.MainViewModelFactory
import com.github.alexdochioiu.main_feature_common_objects.Cake
import com.github.alexdochioiu.main_feature_repository.di.buildRepositoryComponent
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

        // Just added this because it is in the requirements. A custom decorator can be created if needed/desired
        rvCakes.addItemDecoration(
            CustomDividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        swipeRefreshLayout.setOnRefreshListener {
            triggerCakesRefresh()
        }

        mainViewModel.cakes.observe(this, Observer { observedItems ->
            swipeRefreshLayout.isRefreshing = false
            cakesAdapter.replaceItemsAndNotify(observedItems)
        })

        mainViewModel.failure.observe(this, Observer { errorMessage ->
            swipeRefreshLayout.isRefreshing = false
            cakesAdapter.clearItemsAndNotify()

            AlertDialog.Builder(this) // if we want something fancier, a Fragment extending DialogFragment can be made
                .setTitle(R.string.generic_error)
                .setMessage(errorMessage)
                .setPositiveButton(R.string.retry_button) { dialog, _ ->
                    dialog.dismiss()
                    triggerCakesRefresh()
                }.setNegativeButton(R.string.cancel_button) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        })

        // TODO this is not enough. This works well if the configuration changes but if the activity is destroyed by
        //  the os, the VM will lose the cakes data so our observer will not be notified. There is some new SavedState
        //  library that goes together with the ViewModels() and it can sort out this issue (https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate)
        when (savedInstanceState) {
            null -> triggerCakesRefresh(false)
            else -> {
                if (savedInstanceState.getBoolean(KEY_CHANGING_CONFIGURATION, false)) {
                    triggerCakesRefresh(false)
                }
            }
        }
    }

    override fun onCakeSelected(cake: Cake) {
        // todo ideally we don't want (view) actions triggered from any place other than the viewmodel.
        //          there are multiple solutions to do this (observable pattern, VM implements listener, etc)

        AlertDialog.Builder(this) // if we want something fancier, a Fragment extending DialogFragment can be made
            .setMessage(cake.description)
            .setPositiveButton(R.string.ok_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState!!.putBoolean(KEY_CHANGING_CONFIGURATION, true)
    }

    private fun triggerCakesRefresh(showToast: Boolean = true) {
        if (showToast) {
            Toast.makeText(this, getString(R.string.refreshing_cakes_toast_message), Toast.LENGTH_SHORT).show()
        }

        swipeRefreshLayout.isRefreshing = true
        mainViewModel.updateCakes()
    }

    private val featureComponent: FeatureComponent by lazy {
        DaggerFeatureComponent
            .factory()
            .create(
                buildRepositoryComponent(this.application as CoreApplication), this
            )
    }

    companion object {
        const val KEY_CHANGING_CONFIGURATION: String = "is.changing.config"

        @JvmStatic
        fun featureComponent(activity: MainActivity) =
            activity.featureComponent

        fun MainActivity.inject() {
            featureComponent(this).inject(this)
        }
    }
}
