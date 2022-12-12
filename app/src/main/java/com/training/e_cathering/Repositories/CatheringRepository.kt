package com.training.e_cathering.Repositories

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Utils.DataAPIWrapper
import com.training.e_cathering.network.CatheringAPI
import javax.inject.Inject

class CatheringRepository @Inject constructor(private val catheringAPI: CatheringAPI) {
    suspend fun getAllCatherings() : DataAPIWrapper<Cathering, Boolean, Exception>{
        val data = DataAPIWrapper<Cathering, Boolean, Exception>()
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

}