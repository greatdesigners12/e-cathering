package com.training.e_cathering.network

import com.training.e_cathering.Models.*
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface CatheringAPI {
    @GET("catherings")
    suspend fun getAllCatherings() : Response<Cathering>

    @GET("searchAll/{search}")
    suspend fun searchAll(@Path("search") search : String, @Header("token") token: String) : SearchResult

    @GET("getCathering/{cathering_id}")
    suspend fun getCatheringById(@Path("cathering_id") id : String, @Header("token") token : String) : Response<Cathering>

    @GET("getCProfile/{user_id}")
    suspend fun getCatheringProfile(@Path("user_id") id : String, @Header("token") token : String) : SingleResponseData<Cathering>

    @GET("{cathering_id}/getAllDailyProducts")
    suspend fun getAllProducts(@Path("cathering_id") id : String, @Header("token") token : String) : Response<Product>

    @GET("getCatheringByGenre/{genre}")
    suspend fun getAllCatheringsByGenre(@Path("genre") genre : String, @Header("token") token : String) : Response<CatheringWithRating>

    @POST("/updateCathering/{user_id}")
    suspend fun  updateCatheringProfile(@Body cathering: Cathering, @Path("user_id") id:String, @Header("token") token: String): SingleResponseData<Cathering>

    @POST("/createCathering")
    suspend fun createCathering(@Body createCathering: CreateCathering) : SingleResponseData<CreateCathering>
}