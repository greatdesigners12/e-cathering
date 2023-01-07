package com.training.e_cathering.Models


import com.google.gson.annotations.SerializedName

data class TransactionGroup(
    @SerializedName("cathering_id")
    val catheringId: Int,
    @SerializedName("Cathering")
    val Cathering: Cathering,
    @SerializedName("dateTransaction")
    val dateTransaction: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_transaction")
    val idTransaction: String,
    @SerializedName("total_price")
    val totalPrice: Int,
    @SerializedName("shipping_price")
    val shipping_price: Int,
    @SerializedName("TransactionGroupRelation")
    val transactionGroupRelation: List<TransactionGroupRelation>,
    @SerializedName("full_address")
    val full_address: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("User")
    val User: User,
    @SerializedName("status")
    val status: String
)