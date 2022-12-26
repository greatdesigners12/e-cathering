package com.training.e_cathering.network

import com.training.e_cathering.Models.*
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ProductAPI {
    @POST("createProduct")
    suspend fun createProduct(@Body product : Product, @Header("token") token : String) : SingleResponseData

}