package com.training.e_cathering.network

import com.training.e_cathering.Models.Auth
import com.training.e_cathering.Models.LoginJwtToken
import com.training.e_cathering.Models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface AuthAPI {
    @POST("register")
    suspend fun register(@Body user : User) : Auth

    @POST("login")
    suspend fun login(@Body user : User) : LoginJwtToken
}