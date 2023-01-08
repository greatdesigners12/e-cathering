package com.training.e_cathering.Screens.CatheringApprovalScreen

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Models.CatheringWithRating
import com.training.e_cathering.Models.Response
import com.training.e_cathering.Repositories.CatheringRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatheringApprovalViewModel @Inject constructor(val catheringRepository: CatheringRepository) : ViewModel() {
    private val _catheringList = MutableSharedFlow<List<Cathering>>()
    val catheringList = _catheringList.asSharedFlow()
    private val _catheringData = MutableSharedFlow<Cathering>()
    val catheringData = _catheringData.asSharedFlow()

    fun getAllCatherings() {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                catheringRepository.getAllCatherings().data?.data?.let { _catheringList.emit(it) }
            }catch (e : java.lang.Exception){
                Log.d(ContentValues.TAG, "getAllCathering: ${e.message}")
            }


        }
    }

    fun updateVerifiedCathering(cathering: Cathering, cathering_id : String, token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    catheringRepository.updateVerifiedCathering(cathering,cathering_id, it).data?.let { it1 ->
                        _catheringData.emit(
                            it1.data
                        )
                    }
                    getAllCatherings()


                }
            }

        }
    }


}