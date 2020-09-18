package com.gvfiqst.tontestapp.data.util


object OmdbPosterUriTransform {

    fun transform(input: String): String {
        return input.replace("._V1_SX300", "")
    }

}
