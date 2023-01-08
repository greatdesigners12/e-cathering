package com.training.e_cathering.network

import com.training.e_cathering.Models.*
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface ReviewAPI {
    @POST("createUserReviews")
    suspend fun createReview(@Body review : Review, @Header("token") token : String) : SingleResponseData<Review>

    @GET("getUserReview")
    suspend fun getUserReview(@Query("user_id") user_id : String, @Query("cathering_id") cathering_id : String, @Header("token") token : String) : Response<Review>
}