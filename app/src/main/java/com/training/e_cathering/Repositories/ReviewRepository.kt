package com.training.e_cathering.Repositories

import android.content.ContentValues
import android.util.Log
import com.training.e_cathering.Models.Response
import com.training.e_cathering.Models.Review
import com.training.e_cathering.Models.SingleResponseData
import com.training.e_cathering.Models.TransactionRequest
import com.training.e_cathering.Utils.DataAPIWrapper
import com.training.e_cathering.network.ReviewAPI
import javax.inject.Inject

class ReviewRepository @Inject constructor(val reviewAPI: ReviewAPI) {
    suspend fun createReview(review: Review, token : String) : DataAPIWrapper<SingleResponseData<Review>, Boolean, Exception>{
        val data = DataAPIWrapper<SingleResponseData<Review>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = reviewAPI.createReview(review, token)
            Log.d(ContentValues.TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(ContentValues.TAG, "getAllCatherings: ${e.message}")
        }
        return data
    }

    suspend fun getUserReview(user_id : String, cathering_id : String, token: String) : DataAPIWrapper<Response<Review>, Boolean, Exception>{
        val data = DataAPIWrapper<Response<Review>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = reviewAPI.getUserReview(user_id, cathering_id, token)
            Log.d(ContentValues.TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(ContentValues.TAG, "getAllCatherings: ${e.message}")
        }
        return data
    }
}