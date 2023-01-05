package com.training.e_cathering.Screens.HistoryScreenActivity

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
class HistoryTransactionViewModel @Inject constructor(val transactionRepository: TransactionRepository) : ViewModel() {

    val _transactionGroups = MutableSharedFlow<List<TransactionGroup>>()
    val transactionGroups = _transactionGroups.asSharedFlow()

    fun getAllTransactionGroup(user_id : Flow<String?>, token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    user_id.collect{user_id ->
                        if(user_id != null){
                            transactionRepository.getAllTransactionGroup(user_id, it).data?.let { it1 ->
                                _transactionGroups.emit(
                                    it1.data)
                            }
                        }
                    }
                }
            }
        }
    }
}