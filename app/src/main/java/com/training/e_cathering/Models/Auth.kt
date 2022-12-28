package com.training.e_cathering.Models


import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)