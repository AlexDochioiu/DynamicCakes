package com.github.alexdochioiu.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.github.alexdochioiu.core.navigation.Features

internal class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO this can be a splash activity

        //approach 1 : In my view it is better in this particular case since we do not need to inflate a layout
        //                 and it also allows us to better manage the flow between dynamic features provided on demand
        navigateToFeature(Features.Main)


        //approach 2 : Using the new navigation component
        //setContentView(R.layout.activity_entry)
        //findNavController(R.id.nav_host).navigate(R.id.navigation_main_feature)
    }
}
