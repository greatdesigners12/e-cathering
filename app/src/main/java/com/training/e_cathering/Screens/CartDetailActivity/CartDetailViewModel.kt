package com.training.e_cathering.Screens.CartDetailActivity

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.CartWithRelationship
import com.training.e_cathering.Repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartDetailViewModel @Inject constructor(val cartRepository: CartRepository) : ViewModel(){
    private val _cartProducts = MutableSharedFlow<List<CartWithRelationship>>()
    val cartProducts = _cartProducts.asSharedFlow()

    fun getAllCartProducts(user_id : Flow<String>, cathering_id : String, token : Flow<String>){
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
}