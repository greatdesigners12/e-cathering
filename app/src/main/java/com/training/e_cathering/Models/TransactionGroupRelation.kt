package com.training.e_cathering.Models


import com.google.gson.annotations.SerializedName

data class TransactionGroupRelation(
    @SerializedName("id")
    val id: Int,
    @SerializedName("transaction_group_id")
    val transactionGroupId: Int,
    @SerializedName("TransactionProduct")
    val transactionProduct: TransactionProduct,
    @SerializedName("transaction_product_id")
    val transactionProductId: Int
)