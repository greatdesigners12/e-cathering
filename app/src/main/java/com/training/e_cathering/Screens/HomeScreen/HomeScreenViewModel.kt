package com.training.e_cathering.Screens.HomeScreen

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Repositories.CatheringRepository
import com.training.e_cathering.Utils.DataAPIWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: CatheringRepository) : ViewModel(){
    private val _catheringList = MutableSharedFlow<DataAPIWrapper<Cathering, Boolean, Exception>>()
    val catheringList = _catheringList.asSharedFlow()
    fun getAllCatherings() {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _catheringList.emit(repository.getAllCatherings())
            }catch (e : java.lang.Exception){
                Log.d(ContentValues.TAG, "getAllBook: ${e.message}")
            }


        }
    }
}