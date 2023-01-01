package com.training.e_cathering.Models

import com.google.gson.annotations.SerializedName

data class CartWithRelationship(
    @SerializedName("Cathering")
    val Cathering: Cathering,
    @SerializedName("Product")
    val Product: Product,
    @SerializedName("User")
    val User: User,
    @SerializedName("cathering_id")
    val cathering_id: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_id")
    val product_id: Int,
    @SerializedName("user_id")
    val user_id: Int
)

data class Cart(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cathering_id")
    val cathering_id: Int,
    @SerializedName("product_id")
    val product_id: Int,
    @SerializedName("user_id")
    val user_id: Int
)

data class ProductsWithCartChecker(
    @SerializedName("carts")
    val carts: List<Int>,
    @SerializedName("data")
    val data: List<Product>,
    @SerializedName("status")
    val status: String,
    @SerializedName("statusCode")
    val statusCode: Int
)