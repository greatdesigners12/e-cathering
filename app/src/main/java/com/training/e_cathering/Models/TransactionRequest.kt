package com.training.e_cathering.Models

import com.google.gson.annotations.SerializedName

data class TransactionRequest(
    @SerializedName("products")
    val products: List<TransactionProduct>,
    @SerializedName("carts_id")
    val carts_id: List<Int>,
    @SerializedName("cathering_id")
    val cathering_id: Int,
    @SerializedName("user_id")
    var user_id: Int,
    @SerializedName("shipping_price")
    val shipping_price: Int,
    @SerializedName("total_price")
    val total_price: Int,
    @SerializedName("full_address")
    val full_address: String
)