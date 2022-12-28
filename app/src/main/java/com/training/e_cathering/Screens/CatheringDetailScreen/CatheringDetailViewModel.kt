package com.training.e_cathering.Screens.CatheringDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Repositories.CatheringRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatheringDetailViewModel @Inject constructor(val catheringRepository: CatheringRepository) : ViewModel(){

    private val _catheringData = MutableSharedFlow<Cathering>()
    val catheringData = _catheringData.asSharedFlow()

    fun getCatheringById(id : String, token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO) {
            token.collect{
                if(it != null){
                    catheringRepository.getCatheringById(id, it).data?.let { it1 ->
                        _catheringData.emit(
                            it1.data[0])
                    }

                }

            }
        }
    }
}