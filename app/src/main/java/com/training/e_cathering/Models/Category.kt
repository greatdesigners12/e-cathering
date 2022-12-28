package com.training.e_cathering.Models


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nama_kategori")
    val namaKategori: String,
    @SerializedName("image")
    val image: String
)