package com.training.e_cathering.Models

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("user_id")
    var user_id: Int?,
    @SerializedName("cathering_id")
    val cathering_id: Int,
    @SerializedName("review_msg")
    val review_msg: String,
    @SerializedName("rating")
    val rating: Float
)