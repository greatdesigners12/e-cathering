package com.training.e_cathering.Screens.ProductManagementScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.Product
import com.training.e_cathering.Models.Response
import com.training.e_cathering.Models.SingleResponseData
import com.training.e_cathering.Repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductManagementViewModel @Inject constructor(val repository: ProductRepository )  : ViewModel() {
    private val _productList = MutableSharedFlow<Response<Product>>()
    val productList = _productList.asSharedFlow()
    private val _deleteProductStatus = MutableSharedFlow<SingleResponseData<Product>>()
    val deleteProductStatus = _productList.asSharedFlow()
    fun getAllProductByUserId(user_id : Flow<String?> , token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO) {
            user_id.collect{user_id ->
                if(user_id != null){
                    token.collect{
                        if(it != null){
                            repository.getProductByUserId(user_id, it).data?.let { it1 ->
                                _productList.emit(
                                    it1
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun deleteProductById(id : String, user_id: Flow<String?>, token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO) {

            token.collect{

                if(it != null){

                    repository.deleteProductById(id, it).data?.let { it1 ->
                        _deleteProductStatus.emit(
                            it1
                        )
                    }
                }

            }

        }

    }
}