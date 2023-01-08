package com.training.e_cathering.Models

import com.google.gson.annotations.SerializedName

data class CreateCathering(
    @SerializedName("Close")
    val Close: String,
    @SerializedName("Deskripsi")
    val Deskripsi: String,
    @SerializedName("Email")
    val Email: String,
    @SerializedName("Image_logo")
    val Image_logo: String,
    @SerializedName("Image_menu")
    val Image_menu: String,
    @SerializedName("Is_verified")
    val Is_verified: String,
    @SerializedName("Nama")
    val Nama: String,
    @SerializedName("Open")
    val Open: String,
    @SerializedName("Password")
    val Password: String,
    @SerializedName("Role")
    val Role: String,
    @SerializedName("Tanggal_register")
    val Tanggal_register: String,
    @SerializedName("User_id")
    val User_id: Int
)