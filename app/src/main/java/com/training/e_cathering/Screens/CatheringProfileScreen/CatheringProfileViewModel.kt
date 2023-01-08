package com.training.e_cathering.Screens.CatheringProfileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.*
import com.training.e_cathering.Repositories.CatheringRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatheringProfileViewModel @Inject constructor(val catheringRepository: CatheringRepository) : ViewModel() {

    private val _catheringData = MutableSharedFlow<Cathering>()
    val catheringData = _catheringData.asSharedFlow()


    fun getCProfileById(user_id : Flow<String?> , token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO) {
            token.collect{
                if(it != null){
                    user_id.collect{
                        user_id->
                        user_id?.let { it1 ->
                            catheringRepository.getCProfileById(it1, it).data?.let { it1 ->
                                _catheringData.emit(
                                    it1.data)
                            }
                        }
                    }


                }

            }
        }
    }

    fun updateCatheringProfile(cathering: Cathering, user_id : Flow<String?>, token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    user_id.collect{
                        user_id->
                        if (user_id != null) {
                            catheringRepository.updateCatheringProfile(cathering,user_id, it).data?.let { it1 ->
                                _catheringData.emit(
                                    it1.data
                                )
                            }

                        }
                        }
                    }

                }
            }

        }
    }
