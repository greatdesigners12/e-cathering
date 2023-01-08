package com.training.e_cathering.Repositories

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

    suspend fun getAllCatheringByPopularity(limit : Int, token : String) : DataAPIWrapper<Response<CatheringWithRating>, Boolean, Exception>{
        val data = DataAPIWrapper<Response<CatheringWithRating>, Boolean, Exception>()
        data.loading = true
        try{
            data.data = catheringAPI.getAllCatheringByPopularity(limit, token)
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

    suspend fun getProductsWithCartChecker(cathering_id : Int, order_price : String, product_type : String, user_id : Int, token: String) : DataAPIWrapper<ProductsWithCartChecker, Boolean, Exception>{
        val data = DataAPIWrapper<ProductsWithCartChecker, Boolean, Exception>()
        try {
            Log.d(TAG, "getProductsWithCartChecker: ${order_price}")
            data.data = productAPI.getProductWithCartChecker(user_id, order_price, product_type, cathering_id, token)
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