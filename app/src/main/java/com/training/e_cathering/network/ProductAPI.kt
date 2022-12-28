package com.training.e_cathering.network

import com.training.e_cathering.Models.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ProductAPI {
    @POST("createProduct")
    suspend fun createProduct(@Body product : Product, @Header("token") token : String) : SingleResponseData<Product>

    @GET("getProduct/{product_id}")
    suspend fun getProductById(@Path("product_id") productId : String, @Header("token") token : String) : SingleResponseData<Product>

}