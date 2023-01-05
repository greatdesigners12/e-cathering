package com.training.e_cathering.Models

import com.google.gson.annotations.SerializedName

data class TransactionProduct(

    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("time")
    val time: Int,

)