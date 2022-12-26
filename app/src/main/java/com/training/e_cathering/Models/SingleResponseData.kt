package com.training.e_cathering.Models


import com.google.gson.annotations.SerializedName

data class SingleResponseData(
    @SerializedName("data")
    val data: Any,
    @SerializedName("status")
    val status: String,
    @SerializedName("statusCode")
    val statusCode: Int
)