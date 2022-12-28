package com.training.e_cathering.Models


import com.google.gson.annotations.SerializedName

data class Cathering(
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_logo")
    val imageLogo: String,
    @SerializedName("image_menu")
    val imageMenu: String,
    @SerializedName("is_verified")
    val isVerified: Int,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("tanggal_register")
    val tanggalRegister: String,
    @SerializedName("user_id")
    val userId: String
)