package com.gvfiqst.tontestapp.presentation.utils

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import java.util.*
import java.util.concurrent.TimeUnit


fun EditText.onTextChanged(delay: Long = 0, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, block: (String) -> Unit): TextWatcher {
    val realDelay = timeUnit.toMillis(delay)

    return if (realDelay > 0) {
        val watcher = DelayedTextWatcher(realDelay, block)
        addTextChangedListener(watcher)
        return watcher
    } else {
        doAfterTextChanged {
            val text = it?.toString() ?: return@doAfterTextChanged
            block(text)
        }
    }
}

private class DelayedTextWatcher(val delay: Long, val onTextChange: (String) -> Unit): TextWatcher {

    init {
        if (delay <= 0) {
            throw IllegalArgumentException("delay cannot be <= 0. Supplied $delay")
        }
    }

    private var timer = Timer()
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun afterTextChanged(s: Editable?) {
        val text = s?.toString() ?: return

        timer.cancel()
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post { onTextChange(text) }
            }
        }, delay)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

}
