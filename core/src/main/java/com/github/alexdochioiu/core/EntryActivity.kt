package com.github.alexdochioiu.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.alexdochioiu.core.navigation.Features

internal class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO this can be a splash activity

        //approach 1 : In my view it is better in this particular case since we do not need to inflate a layout
        //                 and it also allows us to better manage the flow between dynamic features provided on demand
        navigateToFeature(Features.Dashboard)

    }
}
