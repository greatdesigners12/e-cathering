package com.training.e_cathering.network

import com.training.e_cathering.Models.Category
import com.training.e_cathering.Models.Response
import retrofit2.http.GET
import retrofit2.http.Header
import javax.inject.Singleton

@Singleton
interface CategoryApi {
    @GET("getAllCategories")
    suspend fun getAllCategories(@Header("token") token : String) : Response<Category>
}