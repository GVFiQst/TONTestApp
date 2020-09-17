package com.gvfiqst.tontestapp.data.util

object OmdbPosterUriTramsform {

    fun transform(input: String): String {
        return input.replace("._V1_SX300", "")
    }

}