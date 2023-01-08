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


    @GET("/getAllPaidGroup/{user_id}")
    suspend fun  getAllPaidGroup(@Path("user_id") user_id : Int, @Header("token") token : String) : Response<TransactionGroup>

    @GET("/getDetailPaidGroups/{transaction_group_id}")
    suspend fun  getDetailPaidGroups(@Path("transaction_group_id") id : Int, @Header("token") token : String) : Response<TransactionProduct>

    @POST("/updatePaidGroups/{id}")
    suspend fun  updatePaidGroups(@Body transactionGroup: TransactionGroup,@Path("id") id : Int, @Header("token") token : String) : SingleResponseData<TransactionGroup>

    @GET("setTransaction/success/{id_transaction}")
    suspend fun setToSuccess(@Path("id_transaction") id_transaction : Int, @Header("token") token : String) : SingleResponseData<TransactionGroup>

    @GET("setSnapToken/{id_transaction}")
    suspend fun setSnapToken(@Path("id_transaction") id_transaction : Int, @Query("snap_token") snap_token : String, @Header("token") token : String) : SingleResponseData<TransactionGroup>

    @GET("resetIdTransaction/{id_transaction}")
    suspend fun resetIdTransaction(@Path("id_transaction") id_transaction : String, @Header("token") token : String) : SingleResponseData<String>

}