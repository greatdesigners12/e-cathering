package com.training.e_cathering.Models


import com.google.gson.annotations.SerializedName

data class UserInformation(
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_profile")
    val imageProfile: String,
    @SerializedName("is_verified")
    val isVerified: Int,
    @SerializedName("mama_lengkap")
    val mamaLengkap: String,
    @SerializedName("tanggal_lahir")
    val tanggalLahir: String,
    @SerializedName("user_id")
    val userId: Int
)