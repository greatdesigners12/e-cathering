package com.training.e_cathering.Screens.CreateCatheringScreenActivity

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.training.e_cathering.Models.*
import com.training.e_cathering.Repositories.AuthRepository
import com.training.e_cathering.Repositories.CatheringRepository
import com.training.e_cathering.Utils.DataAPIWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class CreateCatheringScreenActivityViewModel @Inject constructor(private val catheringRepository: CatheringRepository) : ViewModel() {
    private val _registerStatus = MutableSharedFlow<DataAPIWrapper<SingleResponseData<CreateCathering>, Boolean, Exception>>()
    val registerStatus = _registerStatus.asSharedFlow()



    fun register(createCathering: CreateCathering, logoUri : Uri, menuUri : Uri) {
        val storage = Firebase.storage
        val storageRef = storage.reference.child("catherings/" + md5Hash(createCathering.Image_logo + createCathering.User_id) + ".jpg")
        val storageRef2 = storage.reference.child("catherings/" + md5Hash(createCathering.Image_menu + createCathering.User_id) + ".jpg")
        val uploadImageResult = storageRef.putFile(logoUri)
        val uploadImageResult2 = storageRef2.putFile(menuUri)
        uploadImageResult.continueWithTask{task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageRef.downloadUrl
        }.addOnCompleteListener{task ->
            if(task.isSuccessful){
                uploadImageResult2.continueWithTask{task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    storageRef2.downloadUrl
                }.addOnCompleteListener{task ->
                    if(task.isSuccessful){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _registerStatus.emit(catheringRepository.createCathering(createCathering))
            } catch (e: java.lang.Exception) {
                Log.d(ContentValues.TAG, "getAllBook: ${e.message}")
            }


        }}}}}
    }
    fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }

}