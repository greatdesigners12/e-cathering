package com.training.e_cathering.Repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Models.Response
import com.training.e_cathering.Models.SingleResponseData
import com.training.e_cathering.Utils.DataAPIWrapper
import com.training.e_cathering.network.CatheringAPI
import javax.inject.Inject

class CatheringRepository @Inject constructor(private val catheringAPI: CatheringAPI) {
    suspend fun getAllCatherings() : DataAPIWrapper<Response<Cathering>, Boolean, Exception>{
        val data = DataAPIWrapper<Response<Cathering>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = catheringAPI.getAllCatherings()
            Log.d(TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(TAG, "getAllCatherings: ${e.message}")
        }
        return data

    }

    suspend fun getCatheringById(id : String, token : String) : DataAPIWrapper<Response<Cathering>, Boolean, Exception>{
        val data = DataAPIWrapper<Response<Cathering>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = catheringAPI.getCatheringById(id, token)
            Log.d(TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(TAG, "getAllCatherings: ${e.message}")
        }

        return data

    }



}