package com.github.alexdochioiu.about_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class AboutFragment : Fragment() {

    private lateinit var aboutViewModel: AboutViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutViewModel =
            ViewModelProviders.of(this).get(AboutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_about, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        aboutViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}