package com.training.e_cathering.Models

import com.google.gson.annotations.SerializedName

data class TransactionProduct(

    @SerializedName("name")
    var name: String,
    @SerializedName("price")
    var price: Int,
    @SerializedName("time")
    var time: Int,
    @SerializedName("portion")
    var portion: Int,


)