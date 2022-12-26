package com.training.e_cathering.Screens.CreateProductScreen

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.training.e_cathering.Models.Product
import com.training.e_cathering.Repositories.AuthRepository
import com.training.e_cathering.Repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    fun createProduct(product: Product, token : Flow<String?>, imageUri : Uri){

            val storage = Firebase.storage
            val storageRef = storage.reference.child("products/" + md5Hash(product.nama + product.catheringId) + ".jpg")
            val uploadImageResult = storageRef.putFile(imageUri)
            uploadImageResult.addOnSuccessListener {task ->
                viewModelScope.launch(Dispatchers.IO) {
                    token.collect{
                        if(it != null){
                            product.image = storageRef.downloadUrl.result.toString()
                            Log.d(TAG, "createProduct: ${product.image}")
                            repository.createProduct(product, it)
                        }

                    }
                }

            }.addOnFailureListener{
                Log.d(TAG, "createProduct: ${it.message}")
            }



    }

    fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }
}