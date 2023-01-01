package com.training.e_cathering.Repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.training.e_cathering.Models.Category
import com.training.e_cathering.Models.Product
import com.training.e_cathering.Models.Response
import com.training.e_cathering.Models.SingleResponseData
import com.training.e_cathering.Utils.DataAPIWrapper
import com.training.e_cathering.network.CategoryApi
import com.training.e_cathering.network.CatheringAPI
import com.training.e_cathering.network.ProductAPI
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryAPI: CategoryApi) {
    suspend fun getAllCategories(token : String) : DataAPIWrapper<Response<Category>, Boolean, Exception>{
        val data = DataAPIWrapper<Response<Category>, Boolean, Exception>()
        try {
            data.data = categoryAPI.getAllCategories(token)
            data.loading = false
            Log.d(TAG, "getAllCategories: success")
        }catch (e : Exception){
            data.loading = false
            data.e = e
            Log.d(TAG, "getAllCategories: ${e.message}")
        }
        return data
    }
}