package com.training.e_cathering.Repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.training.e_cathering.Models.Data
import com.training.e_cathering.Models.Response
import com.training.e_cathering.Utils.DataAPIWrapper
import com.training.e_cathering.network.CatheringAPI
import com.training.e_cathering.network.ProductAPI
import javax.inject.Inject

class CatheringRepository @Inject constructor(private val catheringAPI: CatheringAPI) {
    suspend fun getAllCatherings() : DataAPIWrapper<Response<Data>, Boolean, Exception>{
        val data = DataAPIWrapper<Response<Data>, Boolean, Exception>()
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