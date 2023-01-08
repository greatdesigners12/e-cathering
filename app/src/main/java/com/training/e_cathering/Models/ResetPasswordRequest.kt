package com.training.e_cathering.Models

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest (
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("oldPassword")
    val oldPassword: String,
    @SerializedName("newPassword")
    val newPassword: String

    )