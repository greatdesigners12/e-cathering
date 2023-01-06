package com.training.e_cathering.Repositories

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.training.e_cathering.Models.*
import com.training.e_cathering.Utils.DataAPIWrapper
import com.training.e_cathering.network.AuthAPI
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApi: AuthAPI) {
    suspend fun register(user : User) : DataAPIWrapper<Auth, Boolean, Exception> {
        val data = DataAPIWrapper<Auth, Boolean, Exception>()
        data.loading = true
        try{

            data.data = authApi.register(user)
            Log.d(ContentValues.TAG, "auth: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(ContentValues.TAG, "auth: ${e.message}")
        }
        return data

    }

    suspend fun login(user : User) : DataAPIWrapper<LoginJwtToken, Boolean, Exception> {
        val data = DataAPIWrapper<LoginJwtToken, Boolean, Exception>()
        data.loading = true
        try{
            data.data = authApi.login(user)
            data.loading = false
            Log.d(TAG, "login: success")
        }catch(e : Exception){
            data.e = e
            data.data = LoginJwtToken("","","",null, "")
            Log.d(TAG, "login: ${e.message}")
        }
        return data

    }

    suspend fun resetPassword(request : ResetPasswordRequest) : DataAPIWrapper<SingleResponseData<User>, Boolean, Exception> {
        val data = DataAPIWrapper<SingleResponseData<User>, Boolean, Exception>()
        data.loading = true
        try{
            data.data = authApi.resetPassword(request)
            data.loading = false
            Log.d(TAG, "login: success")
        }catch(e : Exception){
            data.e = e

            Log.d(TAG, "login: ${e.message}")
        }
        return data

    }

}