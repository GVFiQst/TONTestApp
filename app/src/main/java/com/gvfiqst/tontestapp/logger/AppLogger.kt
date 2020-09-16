package com.gvfiqst.tontestapp.logger

import android.util.Log
import com.gvfiqst.tontestapp.domain.misc.Logger


class AppLogger : Logger {

    override fun e(tag: String, msg: String, e: Throwable?) {
        if (e != null) {
            Log.e(tag, msg, e)
        } else {
            Log.e(tag, msg)
        }
    }

    override fun d(tag: String, msg: String, e: Throwable?) {
        if (e != null) {
            Log.d(tag, msg, e)
        } else {
            Log.d(tag, msg)
        }
    }

    override fun i(tag: String, msg: String, e: Throwable?) {
        if (e != null) {
            Log.i(tag, msg, e)
        } else {
            Log.i(tag, msg)
        }
    }

    override fun v(tag: String, msg: String, e: Throwable?) {
        if (e != null) {
            Log.v(tag, msg, e)
        } else {
            Log.v(tag, msg)
        }
    }

    override fun w(tag: String, msg: String, e: Throwable?) {
        if (e != null) {
            Log.w(tag, msg, e)
        } else {
            Log.w(tag, msg)
        }
    }

}
