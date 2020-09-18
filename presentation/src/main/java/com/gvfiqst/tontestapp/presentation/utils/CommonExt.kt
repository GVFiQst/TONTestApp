package com.gvfiqst.tontestapp.presentation.utils

import android.os.SystemClock
import android.view.View


val Any.loggerTag: String get() = this.javaClass.simpleName

fun View.singleClickListener(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}
