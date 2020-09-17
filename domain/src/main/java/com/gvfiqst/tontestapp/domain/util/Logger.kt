package com.gvfiqst.tontestapp.domain.util


interface Logger {

    fun e(tag: String, msg: String = "", e: Throwable? = null)

    fun d(tag: String, msg: String = "", e: Throwable? = null)

    fun i(tag: String, msg: String = "", e: Throwable? = null)

    fun v(tag: String, msg: String = "", e: Throwable? = null)

    fun w(tag: String, msg: String = "", e: Throwable? = null)

}
