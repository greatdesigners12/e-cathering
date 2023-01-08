package com.training.e_cathering.Screens.CreateProductScreen

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.training.e_cathering.Models.Product
import com.training.e_cathering.Models.SingleResponseData
import com.training.e_cathering.Repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.InputStream
import java.math.BigInteger
import java.net.URL
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class productViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    private val _curProduct = MutableSharedFlow<SingleResponseData<Product>>()
    val curProduct = _curProduct.asSharedFlow()

    fun createProduct(product: Product, token : Flow<String?>, imageUri : Uri){

            val storage = Firebase.storage
            val storageRef = storage.reference.child("products/" + md5Hash(product.nama + product.catheringId) + ".jpg")
            val uploadImageResult = storageRef.putFile(imageUri)
            uploadImageResult.continueWithTask{task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                storageRef.downloadUrl
            }.addOnCompleteListener{task ->
                if(task.isSuccessful){
                    viewModelScope.launch(Dispatchers.IO) {
                        token.collect{
                            if(it != null){
                                product.image = task.result.toString()
                                Log.d(TAG, "createProduct: ${product.image}")
                                repository.createProduct(product, it)
                            }

                        }
                    }
                }else{
                    Log.d(TAG, "createProduct: error")
                }


            }


    }

    fun updateProduct(id : String, product : Product, token : Flow<String?>, imageUri : Uri?){
        val storage = Firebase.storage
        val storageRef = storage.reference.child("products/" + md5Hash(product.nama + product.catheringId) + ".jpg")
        Log.d(TAG, "updateProduct: ${imageUri}")


        viewModelScope.launch(Dispatchers.IO) {
            if(imageUri != null){
                if(product.image.contains("firebasestorage.googleapis.com")){
                    val ref = storage.getReferenceFromUrl(product.image)
                    ref.delete()
                }
                val input: InputStream = URL(product.image).openStream()
                val uploadImageResult = storageRef.putStream(input)
                uploadImageResult.continueWithTask{task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    storageRef.downloadUrl
                }.addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        viewModelScope.launch(Dispatchers.IO) {
                            token.collect{
                                if(it != null){
                                    Log.d(TAG, "updateProduct: ${product}")
                                    product.image = task.result.toString()
                                    Log.d(TAG, "createProduct: ${product.image}")
                                    repository.updateProduct(id, product, it)
                                }

                            }
                        }
                    }else{
                        Log.d(TAG, "createProduct: error")
                    }


                }

            }else{


                viewModelScope.launch(Dispatchers.IO) {
                    token.collect{
                        if(it != null){
                            repository.updateProduct(id, product, it)
                        }

                    }
                }
            }
        }



    }

    fun getProductById(id : String, token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO) {
            token.collect{
                if(it != null){
                    repository.getProductDetail(id, it).data?.let { it1 -> _curProduct.emit(it1) }
                }
            }

        }
    }

    fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }
}