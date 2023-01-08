package com.training.e_cathering.Screens.CatheringDetailScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.*
import com.training.e_cathering.Repositories.CartRepository
import com.training.e_cathering.Repositories.CatheringRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatheringDetailViewModel @Inject constructor(val catheringRepository: CatheringRepository, val cartRepository: CartRepository) : ViewModel(){

    private val _catheringData = MutableSharedFlow<Cathering>()
    val catheringData = _catheringData.asSharedFlow()
    private val _productData = MutableSharedFlow<ProductsWithCartChecker>()
    val productData = _productData.asSharedFlow()
    private val _addCartStatus = MutableSharedFlow<SingleResponseData<CartWithRelationship>?>()
    val addCartStatus = _addCartStatus.asSharedFlow()
    private val _removeCartStatus = MutableSharedFlow<SingleResponseData<Cart>>()
    val removeCartStatus = _removeCartStatus.asSharedFlow()

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

    fun getAllProductsWithCartChecker(user_id: Flow<String?>, search : String, price_order : String, product_type : String, cathering_id : String, token : Flow<String?>){

        viewModelScope.launch(Dispatchers.IO) {
            token.collect{token ->
                if(token != null){
                    user_id.collect{
                        if(it != null){
                            catheringRepository.getProductsWithCartChecker(cathering_id.toInt(), search, price_order, product_type ,it.toInt(), token!!).data?.let { it1 ->
                                _productData.emit(
                                    it1
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun removeCart(product_id : Int, cathering_id: Int, user_id : Flow<String?> ,token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO) {
            token.collect{
                if(it != null){
                    user_id.collect{user_id ->
                        if(user_id != null){
                            cartRepository.removeCart(Cart(null, cathering_id, product_id, user_id.toInt()), it).data?.let { it1 ->
                                _removeCartStatus.emit(
                                    it1
                                )
                            }
                        }
                    }
                }

            }
        }
    }

    fun addToCart(user_id : Flow<String?>, id_cathering : Int, id_product : Int, token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO) {
            token.collect{
                if(it != null){
                    user_id.collect{user_id ->
                        if(user_id != null){
                            _addCartStatus.emit(cartRepository.addProductToCart(Cart(null, id_cathering.toInt(), id_product.toInt(), user_id.toInt()), it).data)
                        }
                    }
                }

            }
        }
    }


}