package com.training.e_cathering.Repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.training.e_cathering.Models.Product
import com.training.e_cathering.Models.ProductsWithCartChecker
import com.training.e_cathering.Models.Response
import com.training.e_cathering.Models.SingleResponseData
import com.training.e_cathering.Utils.DataAPIWrapper
import com.training.e_cathering.network.CatheringAPI
import com.training.e_cathering.network.ProductAPI
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productAPI: ProductAPI) {

    suspend fun createProduct(product : Product, token : String) : DataAPIWrapper<SingleResponseData<Product>, Boolean, Exception>{
        val data = DataAPIWrapper<SingleResponseData<Product>, Boolean, Exception>()
        try {
            data.data = productAPI.createProduct(product, token)
            data.loading = false
            Log.d(TAG, "createProduct: success")
        }catch (e : Exception){
            data.loading = false
            data.e = e
            Log.d(TAG, "createProduct: ${e.message}")
        }
        return data
    }

    suspend fun getProductDetail(product_id : String, token : String) : DataAPIWrapper<SingleResponseData<Product>, Boolean, Exception>{
        val data = DataAPIWrapper<SingleResponseData<Product>, Boolean, Exception>()
        try {
            data.data = productAPI.getProductById(product_id, token)
            data.loading = false
            Log.d(TAG, "createProduct: success")
        }catch (e : Exception){
            data.loading = false
            data.e = e
            Log.d(TAG, "createProduct: ${e.message}")
        }
        return data
    }

    suspend fun getProductByUserId(user_id : String, token : String) : DataAPIWrapper<Response<Product>, Boolean, Exception>{
        val data = DataAPIWrapper<Response<Product>, Boolean, Exception>()
        try {
            Log.d(TAG, "getProductByUserId: ${user_id}")
            data.data = productAPI.getAllProductByUserId(user_id.toInt(), token)
            data.loading = false
            Log.d(TAG, "createProduct: ${data.data}")
        }catch (e : Exception){
            data.loading = false
            data.e = e
            Log.d(TAG, "createProduct: ${e.message}")
        }
        return data

    }

    suspend fun updateProduct(id_product : String, product: Product, token: String) : DataAPIWrapper<SingleResponseData<Product>, Boolean, Exception>{
        val data = DataAPIWrapper<SingleResponseData<Product>, Boolean, Exception>()
        try {
            Log.d(TAG, "updateProduct: ${product}")
            data.data = productAPI.updateProductById(id_product.toInt(), product,  token)
            data.loading = false
            Log.d(TAG, "createProduct: ${data.data}")
        }catch (e : Exception){
            data.loading = false
            data.e = e
            Log.d(TAG, "createProduct: ${e.message}")
        }
        return data

    }

    suspend fun deleteProductById(id : String, token : String) : DataAPIWrapper<SingleResponseData<Product>, Boolean, Exception>{
        val data = DataAPIWrapper<SingleResponseData<Product>, Boolean, Exception>()
        try {

            data.data = productAPI.deleteProductById(id.toInt(), token)
            data.loading = false
            Log.d(TAG, "createProduct: ${data.data}")
        }catch (e : Exception){
            data.loading = false
            data.e = e
            Log.d(TAG, "createProduct: ${e.message}")
        }
        return data

    }


}