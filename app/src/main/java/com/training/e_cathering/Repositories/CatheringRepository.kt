package com.training.e_cathering.Repositories

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.training.e_cathering.Models.*
import com.training.e_cathering.Utils.DataAPIWrapper
import com.training.e_cathering.network.CatheringAPI
import com.training.e_cathering.network.ProductAPI
import javax.inject.Inject

class CatheringRepository @Inject constructor(private val catheringAPI: CatheringAPI, val productAPI: ProductAPI) {
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

    suspend fun searchAll(search : String, token : String) : DataAPIWrapper<SearchResult, Boolean, Exception>{
        val data = DataAPIWrapper<SearchResult, Boolean, Exception>()
        data.loading = true
        try{

            data.data = catheringAPI.searchAll(search, token)
            Log.d(TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(TAG, "getAllCatherings: ${e.message}")
        }
        return data
    }
    suspend fun  updateCatheringProfile(cathering: Cathering, id: String, token: String): DataAPIWrapper<SingleResponseData<Cathering>, Boolean, Exception>{
        val data = DataAPIWrapper<SingleResponseData<Cathering>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = catheringAPI.updateCatheringProfile(cathering,id, token)
            Log.d(TAG, "updateCatheringProfile: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(TAG, "updateCatheringProfile: ${e.message}")
        }

        return data
    }
    suspend fun  updateVerifiedCathering(cathering: Cathering, id: String, token: String): DataAPIWrapper<SingleResponseData<Cathering>, Boolean, Exception>{
        val data = DataAPIWrapper<SingleResponseData<Cathering>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = catheringAPI.updateCatheringProfile(cathering,id, token)
            Log.d(TAG, "updateCatheringVerified: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(TAG, "updateCatheringVerified: ${e.message}")
        }

        return data
    }
    suspend fun createCathering(createCathering: CreateCathering) : DataAPIWrapper<SingleResponseData<CreateCathering>, Boolean, Exception> {
        val data = DataAPIWrapper<SingleResponseData<CreateCathering>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = catheringAPI.createCathering(createCathering)
            Log.d(ContentValues.TAG, "createCathering: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(ContentValues.TAG, "createCathering: ${e.message}")
        }
        return data

    }


    suspend fun getAllCatheringsByGenre(genre : String, token: String) : DataAPIWrapper<Response<CatheringWithRating>, Boolean, Exception>{
        val data = DataAPIWrapper<Response<CatheringWithRating>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = catheringAPI.getAllCatheringsByGenre(genre, token)
            Log.d(TAG, "getAllCatheringsssss: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(TAG, "getAllCatheringsssss: ${e.message}")
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
    suspend fun getCProfileById(id : String, token : String) : DataAPIWrapper<SingleResponseData<Cathering>, Boolean, Exception>{
        val data = DataAPIWrapper<SingleResponseData<Cathering>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = catheringAPI.getCatheringProfile(id, token)
            Log.d(TAG, "getProfileCathering: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(TAG, "getProfileCathering: ${e.message}")
        }

        return data

    }

    suspend fun getAllProducts(id : String, token: String) : DataAPIWrapper<Response<Product>, Boolean, Exception>{
        val data = DataAPIWrapper<Response<Product>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = catheringAPI.getAllProducts(id, token)
            Log.d(TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(TAG, "getAllCatherings: ${e.message}")
        }

        return data
    }

    suspend fun getProductsWithCartChecker(cathering_id : Int, user_id : Int, token: String) : DataAPIWrapper<ProductsWithCartChecker, Boolean, Exception>{
        val data = DataAPIWrapper<ProductsWithCartChecker, Boolean, Exception>()
        try {
            data.data = productAPI.getProductWithCartChecker(user_id, cathering_id, token)
            data.loading = false
            Log.d(TAG, "createProduct: success")
        }catch (e : Exception){
            data.loading = false
            data.e = e
            Log.d(TAG, "createProduct: ${e.message}")
        }
        return data
    }






}