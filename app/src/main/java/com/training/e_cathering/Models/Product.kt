package com.training.e_cathering.Models


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String?,
    @SerializedName("cathering_id")
    val catheringId: String,
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("harga")
    val harga: Int,
    @SerializedName("image_url")
    var image: String,
    @SerializedName("nama")
    val nama: String
)