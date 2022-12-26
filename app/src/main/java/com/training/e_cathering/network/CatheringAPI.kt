package com.training.e_cathering.network

import com.training.e_cathering.Models.Data
import com.training.e_cathering.Models.Response
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface CatheringAPI {
    @GET("catherings")
    suspend fun getAllCatherings() : Response<Data>


}