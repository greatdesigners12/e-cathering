package com.training.e_cathering.network

import com.training.e_cathering.Models.*
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface TransactionAPI {
    @POST("createTransaction")
    suspend fun createTransaction(@Body transactionRequest : TransactionRequest, @Header("token") token : String) : SingleResponseData<TransactionRequest>

    @GET("getTransactionGroup/{id}")
    suspend fun getTransactionGroup(@Path("id") user_id : Int, @Header("token") token : String) : SingleResponseData<TransactionGroup>

    @GET("getAllTransactionGroup/{user_id}")
    suspend fun getAllTransactionGroup(@Path("user_id") user_id : Int, @Header("token") token : String) : Response<TransactionGroup>
}