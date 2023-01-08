package com.training.e_cathering.Screens.HomeScreen

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.Category
import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Models.CatheringWithRating
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
    private val _catheringList = MutableSharedFlow<List<Cathering>>()
    val catheringList = _catheringList.asSharedFlow()
    private val _categoryList = MutableSharedFlow<List<Category>>()
    val categoryList = _categoryList.asSharedFlow()
    private val _popularCathering = MutableSharedFlow<List<CatheringWithRating>>()
    val popularCathering = _popularCathering.asSharedFlow()
    fun searchAll(search : String, token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    val data = catheringRepository.searchAll(search, it)
                    Log.d(TAG, "searchAll: ${search}")
                    data.data?.let { it1 -> _catheringList.emit(it1.catherings) }
                    data.data?.categories?.let { it1 -> _categoryList.emit(it1) }
                }
            }
        }
    }

    fun getAllCatherings() {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                catheringRepository.getAllCatherings().data?.data?.let { _catheringList.emit(it) }
            }catch (e : java.lang.Exception){
                Log.d(ContentValues.TAG, "getAllBook: ${e.message}")
            }


        }
    }

    fun getAllCatheringsByRating(limit : Int, token: Flow<String?>) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                token.collect{
                    if(it != null){
                        catheringRepository.getAllCatheringByPopularity(limit, it).data?.data?.let { _popularCathering.emit(it) }
                    }
                }
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
                        categoryRepository.getAllCategories(it).data?.data?.let { it1 ->
                            _categoryList.emit(
                                it1
                            )
                        }
                    }
                }

            }catch (e : java.lang.Exception){
                Log.d(ContentValues.TAG, "getAllBook: ${e.message}")
            }


        }
    }
}