package com.training.e_cathering.network

import com.training.e_cathering.Models.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface CartAPI {
    @POST("createCart")
    suspend fun createCart(@Body cart : Cart, @Header("token") token : String) : SingleResponseData<CartWithRelationship>

    @DELETE("removeCart")
    suspend fun removeCart(@Query("user_id") user_id : Int, @Query("cathering_id") cathering_id : Int, @Query("product_id") product_id : Int,@Header("token") token : String) : SingleResponseData<Cart>
}