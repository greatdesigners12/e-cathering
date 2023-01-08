package com.training.e_cathering.Screens.TransactionDetailScreen

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.Review
import com.training.e_cathering.Models.TransactionGroup
import com.training.e_cathering.Repositories.ReviewRepository
import com.training.e_cathering.Repositories.TransactionRepository
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(val transactionRepository: TransactionRepository, val reviewRepository: ReviewRepository) : ViewModel() {
    val _transactionGroup = MutableSharedFlow<TransactionGroup>()
    val transactionGroup = _transactionGroup.asSharedFlow()
    val _newIdTransaction =  MutableSharedFlow<String>()
    val newIdTransaction = _newIdTransaction.asSharedFlow()
    val _addReviewStatus =  MutableSharedFlow<Review>()
    val addReviewStatus = _addReviewStatus.asSharedFlow()
    val _userReview =  MutableSharedFlow<List<Review>>()
    val userReview = _userReview.asSharedFlow()

    lateinit var context: Context
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

    fun setSnapToken(transactionGroupId : Int, snap_token: String, token : Flow<String?>){
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



    fun setToSuccess(id_transaction : String, token: Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    transactionRepository.setToSuccess(id_transaction.toInt(), it).data?.let { it1 ->
                        _transactionGroup.emit(
                            it1.data)
                    }

                }
            }
        }
    }

    fun resetIdTransaction(id_transaction : String, token: Flow<String?>){
        Log.d(TAG, "resetIdTransaction: awdaw")
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    transactionRepository.resetIdTransaction(id_transaction, it).data?.let { it1 ->
                        _newIdTransaction.emit(
                            it1.data)
                    }
                    newIdTransaction.collect{
                        Log.d(TAG, "resetIdTransaction: ${it}")

                    }

                }
            }
        }
    }

    fun createReview(review : Review, user_id : Flow<String?>, token: Flow<String?>){
        Log.d(TAG, "resetIdTransaction: awdaw")
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    user_id.collect{u_id ->
                        if(u_id != null){
                            review.user_id = u_id.toInt()
                            reviewRepository.createReview(review, it).data?.let { it1 ->
                                _addReviewStatus.emit(
                                    it1.data)
                            }
                            getReview(user_id, review.cathering_id.toString(), token)
                        }

                    }


                }
            }
        }
    }

    fun getReview(user_id : Flow<String?>, cathering_id : String, token: Flow<String?>){
        Log.d(TAG, "resetIdTransaction: awdaw")
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    user_id.collect{user_id ->
                        if(user_id != null){

                            reviewRepository.getUserReview(user_id, cathering_id, it).data?.let { it1 ->
                                _userReview.emit(
                                    it1.data)
                            }
                        }

                    }


                }
            }
        }
    }




}