package com.gvfiqst.tontestapp.data.api.omdb.model

import com.google.gson.annotations.SerializedName


data class MovieInfoResponse(
    @SerializedName("imdbID")
    var imdbId: String? = null,
    @SerializedName("Title")
    var title: String? = null,
    @SerializedName("Year")
    var year: String? = null,
    @SerializedName("Type")
    var type: String? = null,
    @SerializedName("Poster")
    var poster: String? = null
)
