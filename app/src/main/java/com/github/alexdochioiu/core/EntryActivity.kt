package com.github.alexdochioiu.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.alexdochioiu.core.navigation.Features

internal class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_entry)
        navigateToFeature(Features.Main)
    }
}
