package com.training.e_cathering.Models


import com.google.gson.annotations.SerializedName
import java.util.*

data class Product(
    @SerializedName("id")
    val id: String?,
    @SerializedName("cathering_id")
    val catheringId: String?,
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("harga")
    val harga: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("image_url")
    var image: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("start_date")
    var start_date: String,
    @SerializedName("due_date")
    var due_date: String
)