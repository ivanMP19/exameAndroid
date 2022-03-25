package com.ivan.marin.exameandroid.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieModelList(
    @SerializedName(value = "page") @Expose val page:Int,
    @SerializedName(value = "results") @Expose val results: List<MovieModel>,
    @SerializedName(value = "total_results") @Expose val total_results: Int,
    @SerializedName(value = "total_pages") @Expose val total_pages: Int
    )
