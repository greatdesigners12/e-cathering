package com.training.e_cathering.Models


import com.google.gson.annotations.SerializedName

data class Cathering(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("status")
    val status: String,
    @SerializedName("statusCode")
    val statusCode: Int
)