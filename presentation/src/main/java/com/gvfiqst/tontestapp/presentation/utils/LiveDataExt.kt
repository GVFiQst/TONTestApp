package com.gvfiqst.tontestapp.presentation.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


fun <T> LiveData<T>.observeData(lifecycleOwner: LifecycleOwner, block: (T) -> Unit)
        = observe(lifecycleOwner, Observer(block))
