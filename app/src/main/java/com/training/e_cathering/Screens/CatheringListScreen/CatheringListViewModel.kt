package com.training.e_cathering.Screens.CatheringListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Models.CatheringWithRating
import com.training.e_cathering.Models.Response
import com.training.e_cathering.Repositories.CatheringRepository
import com.training.e_cathering.Repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatheringListViewModel @Inject constructor(val catheringRepository: CatheringRepository) : ViewModel() {
    private val _catheringList = MutableSharedFlow<Response<CatheringWithRating>>()
    val catheringList = _catheringList.asSharedFlow()
    private val _catheringData = MutableSharedFlow<Cathering>()
    val catheringData = _catheringData.asSharedFlow()

    fun getCatheringBasedOnGenre(genre : String, token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    catheringRepository.getAllCatheringsByGenre(genre, it).data?.let { it1 ->
                        _catheringList.emit(
                            it1
                        )
                    }
                }
            }

        }
    }




}