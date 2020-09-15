package com.gvfiqst.tontestapp.data.api.omdb.model

import com.google.gson.annotations.SerializedName


data class MovieResponse(
    @SerializedName("imdbID")
    var imdbId: String? = null,
    @SerializedName("Title")
    var title: String? = null
)
