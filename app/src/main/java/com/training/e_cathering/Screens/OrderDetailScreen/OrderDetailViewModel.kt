package com.training.e_cathering.Screens.OrderDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.Response
import com.training.e_cathering.Models.SingleResponseData
import com.training.e_cathering.Models.TransactionGroup
import com.training.e_cathering.Models.TransactionProduct
import com.training.e_cathering.Repositories.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrderDetailViewModel @Inject constructor(val transactionRepository: TransactionRepository) : ViewModel() {
    private val _transactionList = MutableSharedFlow<Response<TransactionProduct>>()
    val transactionList = _transactionList.asSharedFlow()
    private val _catheringdata = MutableSharedFlow<SingleResponseData<TransactionGroup>>()
    val catheringdata = _catheringdata.asSharedFlow()


    fun getDetailPaidGroup(id: String , token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    transactionRepository.getDetailPaidGroups(id, it).data?.let { it1 ->
                        _transactionList.emit(
                            it1
                        )
                    }
                }
            }

        }
    }
    fun getGroupbyId(id: String , token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    transactionRepository.getTransactionGroupById(id.toInt(), it).data?.let { it1 ->
                        _catheringdata.emit(
                            it1
                        )
                    }
                }
            }

        }
    }
    fun updateTransactionGroup(transactionGroup: TransactionGroup, group_id:String, token: Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    transactionRepository.updatePaidGroups(transactionGroup,group_id, it).data?.let { it1 ->
                        _catheringdata.emit(
                            it1
                        )
                    }



                }
            }

        }
    }

}