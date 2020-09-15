package com.gvfiqst.tontestapp.data.api.base

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyInterceptor(private val apiKey: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("lox", "Thread: ${Thread.currentThread().name}")
        val request = chain.request()
        val url = request.url.newBuilder().addQueryParameter(API_KEY, apiKey).build()
        return chain.proceed(request.newBuilder().url(url).build())
    }

    companion object {
        private const val API_KEY = "apikey"
    }

}
