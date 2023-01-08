package com.training.e_cathering.network

import com.training.e_cathering.Models.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ProductAPI {
    @POST("createProduct")
    suspend fun createProduct(@Body product : Product, @Header("token") token : String) : SingleResponseData<Product>

    @GET("getProduct/{product_id}")
    suspend fun getProductById(@Path("product_id") productId : String, @Header("token") token : String) : SingleResponseData<Product>

    @GET("getAllProductsWithCartChecker")
    suspend fun getProductWithCartChecker(@Query("user_id") user_id : Int, @Query("price_order") price_order : String, @Query("product_type") product_type : String, @Query("cathering_id") cathering_id : Int, @Header("token") token: String) : ProductsWithCartChecker

    @GET("getAllProductByUserId/{user_id}")
    suspend fun getAllProductByUserId(@Path("user_id") user_id : Int, @Header("token") token: String) : Response<Product>

    @DELETE("deleteProduct/{product_id}")
    suspend fun deleteProductById(@Path("product_id") product_id : Int, @Header("token") token: String) : SingleResponseData<Product>

    @POST("updateProduct/{product_id}")
    suspend fun updateProductById(@Path("product_id") product_id : Int, @Body product: Product, @Header("token") token: String) : SingleResponseData<Product>
}