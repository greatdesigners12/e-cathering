package com.training.e_cathering.Models


import com.google.gson.annotations.SerializedName

data class SingleResponseData<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("status")
    val status: String,
    @SerializedName("statusCode")
    val statusCode: Int
)