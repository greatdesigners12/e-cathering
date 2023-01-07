package com.training.e_cathering.Screens.TransactionDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.TransactionGroup
import com.training.e_cathering.Repositories.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(val transactionRepository: TransactionRepository) : ViewModel() {
    val _transactionGroup = MutableSharedFlow<TransactionGroup>()
    val transactionGroup = _transactionGroup.asSharedFlow()

    fun getTransactionGroupById(transactionGroupId : Int, token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){

                    transactionRepository.getTransactionGroupById(transactionGroupId, it).data?.let { it1 ->
                        _transactionGroup.emit(
                            it1.data)
                    }

                }
            }
        }
    }
}