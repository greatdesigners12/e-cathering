package com.training.e_cathering.Screens.RegisterScreen

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.Auth
import com.training.e_cathering.Models.User
import com.training.e_cathering.Repositories.AuthRepository
import com.training.e_cathering.Utils.DataAPIWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private val _registerStatus = MutableSharedFlow<DataAPIWrapper<Auth, Boolean, Exception>>()
    val registerStatus = _registerStatus.asSharedFlow()

    fun register(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _registerStatus.emit(repository.register(user))
            }catch (e : java.lang.Exception){
                Log.d(ContentValues.TAG, "getAllBook: ${e.message}")
            }


        }
    }
}