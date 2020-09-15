package com.gvfiqst.tontestapp.data.api.base

import com.google.gson.Gson

object GsonFactory {

    fun create(): Gson {
        return Gson()
    }

}