package com.training.e_cathering.Models


import com.google.gson.annotations.SerializedName

data class LoginJwtToken(
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("role")
    val role: String
)