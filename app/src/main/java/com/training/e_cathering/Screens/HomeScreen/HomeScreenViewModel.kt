package com.training.e_cathering.Screens.HomeScreen

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.Category
import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Models.Response
import com.training.e_cathering.Repositories.CategoryRepository
import com.training.e_cathering.Repositories.CatheringRepository
import com.training.e_cathering.Utils.DataAPIWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val catheringRepository: CatheringRepository, private val categoryRepository: CategoryRepository) : ViewModel(){
    private val _catheringList = MutableSharedFlow<DataAPIWrapper<Response<Cathering>, Boolean, Exception>>()
    val catheringList = _catheringList.asSharedFlow()
    private val _categoryList = MutableSharedFlow<DataAPIWrapper<Response<Category>, Boolean, Exception>>()
    val categoryList = _categoryList.asSharedFlow()
    fun getAllCatherings() {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _catheringList.emit(catheringRepository.getAllCatherings())
            }catch (e : java.lang.Exception){
                Log.d(ContentValues.TAG, "getAllBook: ${e.message}")
            }


        }
    }

    fun getAllCategories(token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                token.collect{
                    if(it != null){
                        _categoryList.emit(categoryRepository.getAllCategories(it))
                    }
                }

            }catch (e : java.lang.Exception){
                Log.d(ContentValues.TAG, "getAllBook: ${e.message}")
            }


        }
    }
}