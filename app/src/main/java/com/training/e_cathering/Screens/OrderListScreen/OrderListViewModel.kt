package com.training.e_cathering.Screens.OrderListScreen

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Models.Response
import com.training.e_cathering.Models.TransactionGroup
import com.training.e_cathering.Repositories.CatheringRepository
import com.training.e_cathering.Repositories.TransactionRepository
import com.training.e_cathering.Utils.DataAPIWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class OrderListViewModel @Inject constructor(val transactionRepository: TransactionRepository) : ViewModel() {
    private val _transactionList = MutableSharedFlow<Response<TransactionGroup>>()
    val transactionList = _transactionList.asSharedFlow()

    fun getAllPaidGroups(user_id: Flow<String?> , token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    user_id.collect{
                            user_id->
                        if (user_id != null) {
                    transactionRepository.getAllPaidGroups(user_id.toInt(), it).data?.let { it1 ->
                        _transactionList.emit(
                            it1
                        )
                    }}}}
                }
            }

        }
    }

