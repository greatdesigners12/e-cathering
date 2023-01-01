package com.training.e_cathering.Models

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("catherings")
    val catherings: List<Cathering>,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("status")
    val status: String,
    @SerializedName("statusCode")
    val statusCode: Int
)