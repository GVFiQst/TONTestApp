package com.gvfiqst.tontestapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gvfiqst.tontestapp.presentation.R
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.scope.lifecycleScope


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory(lifecycleScope)
        super.onCreate(savedInstanceState)
    }

}
