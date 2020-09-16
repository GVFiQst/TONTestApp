package com.gvfiqst.tontestapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gvfiqst.tontestapp.presentation.R
import org.koin.androidx.fragment.android.setupKoinFragmentFactory


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupKoinFragmentFactory()
    }

}
