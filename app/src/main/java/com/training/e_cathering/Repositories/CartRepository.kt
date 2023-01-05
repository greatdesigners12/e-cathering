package com.training.e_cathering.Repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.training.e_cathering.Models.*
import com.training.e_cathering.Utils.DataAPIWrapper
import com.training.e_cathering.network.CartAPI
import com.training.e_cathering.network.CatheringAPI
import javax.inject.Inject

class CartRepository @Inject constructor(private val cartAPI: CartAPI) {

    suspend fun addProductToCart(cart: Cart, token: String) : DataAPIWrapper<SingleResponseData<CartWithRelationship>, Boolean, Exception>{
        val data = DataAPIWrapper<SingleResponseData<CartWithRelationship>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = cartAPI.createCart(cart, token)
            Log.d(TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(TAG, "getAllCatherings: ${e.message}")
        }

        return data
    }

    suspend fun getAllProductCart(user_id: Int, cathering_id: Int, token: String) : DataAPIWrapper<Response<CartRelationshipWithUserInformation>, Boolean, Exception>{
        val data = DataAPIWrapper<Response<CartRelationshipWithUserInformation>, Boolean, Exception>()
        data.loading = true
        try{
            data.data = cartAPI.getAllCartProduct(user_id, cathering_id, token)
            Log.d(TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(TAG, "getAllCatherings: ${e.message}")
        }

        return data
    }

    suspend fun removeCart(cart: Cart, token: String) : DataAPIWrapper<SingleResponseData<Cart>, Boolean, Exception>{
        val data = DataAPIWrapper<SingleResponseData<Cart>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = cartAPI.removeCart(cart.user_id, cart.cathering_id, cart.product_id, token)
            Log.d(TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(TAG, "getAllCatherings: ${e.message}")
        }

        return data
    }

}