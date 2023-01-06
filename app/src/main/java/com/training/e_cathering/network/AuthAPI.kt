package com.training.e_cathering.network

import com.training.e_cathering.Models.*
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

    @POST("resetPassword")
    suspend fun resetPassword(@Body request : ResetPasswordRequest) : SingleResponseData<User>

}