package com.training.e_cathering.Models


import com.google.gson.annotations.SerializedName

data class TransactionGroup(
    @SerializedName("cathering_id")
    val catheringId: Int,
    @SerializedName("dateTransaction")
    val dateTransaction: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_transaction")
    val idTransaction: String,
    @SerializedName("total_price")
    val totalPrice: Int,
    @SerializedName("TransactionGroupRelation")
    val transactionGroupRelation: List<TransactionGroupRelation>,
    @SerializedName("user_id")
    val userId: Int
)