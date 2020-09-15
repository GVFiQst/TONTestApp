package com.gvfiqst.tontestapp.data.api.omdb.model

import com.google.gson.annotations.SerializedName


data class SearchResponse(
    @SerializedName("Search")
    var search: List<MovieResponse> = emptyList(),
    @SerializedName("totalResults")
    var totalResults: Int? = null,
    @SerializedName("Response")
    var isSuccess: Boolean = false,
    @SerializedName("Error")
    var error: String? = null
)
