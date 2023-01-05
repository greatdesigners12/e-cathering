package com.training.e_cathering.Screens.CartDetailActivity

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.*
import com.training.e_cathering.Repositories.CartRepository
import com.training.e_cathering.Repositories.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartDetailViewModel @Inject constructor(val cartRepository: CartRepository, val transactionRepository: TransactionRepository) : ViewModel(){
    private val _cartProducts = MutableSharedFlow<List<CartRelationshipWithUserInformation>>()
    val cartProducts = _cartProducts.asSharedFlow()
    val _createStatus = MutableSharedFlow<SingleResponseData<TransactionRequest>>()
    val createStatus = _createStatus.asSharedFlow()

    fun getAllCartProducts(user_id : Flow<String?>, cathering_id : String, token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            user_id.collect{
                if(it != null){
                    token.collect{token ->
                        if(token != null){
                            cartRepository.getAllProductCart(it.toInt(), cathering_id.toInt(), token).data?.let { it1 ->
                                _cartProducts.emit(
                                    it1.data)
                            }
                        }
                    }
                }
            }
        }
    }

    fun createTransaction(transactionRequest: TransactionRequest, user_id: Flow<String?>, token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    user_id.collect{user_id ->
                        if(user_id != null){
                            transactionRequest.user_id = user_id.toInt()
                            transactionRepository.createTransaction(transactionRequest, it).data?.let { it1 ->
                                _createStatus.emit(
                                    it1
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}