package com.training.e_cathering.network

import com.training.e_cathering.Models.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface CatheringAPI {
    @GET("catherings")
    suspend fun getAllCatherings() : Response<Cathering>

    @GET("searchAll/{search}")
    suspend fun searchAll(@Path("search") search : String, @Header("token") token: String) : SearchResult

    @GET("getCathering/{cathering_id}")
    suspend fun getCatheringById(@Path("cathering_id") id : String, @Header("token") token : String) : Response<Cathering>

    @GET("{cathering_id}/getAllDailyProducts")
    suspend fun getAllProducts(@Path("cathering_id") id : String, @Header("token") token : String) : Response<Product>

    @GET("getCatheringByGenre/{genre}")
    suspend fun getAllCatheringsByGenre(@Path("genre") genre : String, @Header("token") token : String) : Response<CatheringWithRating>

    @GET("getAllCatheringByPopularity")
    suspend fun getAllCatheringByPopularity(@Query("limit") limit : Int, @Header("token") token : String) : Response<CatheringWithRating>

}