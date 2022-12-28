package com.training.e_cathering.network

import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Models.Response
import com.training.e_cathering.Models.SingleResponseData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface CatheringAPI {
    @GET("catherings")
    suspend fun getAllCatherings() : Response<Cathering>

    @GET("getCathering/{cathering_id}")
    suspend fun getCatheringById(@Path("cathering_id") id : String, @Header("token") token : String) : Response<Cathering>

}