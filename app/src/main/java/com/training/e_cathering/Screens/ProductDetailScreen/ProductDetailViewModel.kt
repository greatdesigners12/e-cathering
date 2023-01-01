package com.training.e_cathering.Screens.ProductDetailScreen

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.Product
import com.training.e_cathering.Repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(val productRepository: ProductRepository) : ViewModel() {
    private var _curData = MutableSharedFlow<Product>()
    val curData = _curData.asSharedFlow()

    fun getProductDetail(id : String, token : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            token.collect{
                if(it != null){
                    productRepository.getProductDetail(id, it).data?.let { it1 -> _curData.emit(it1.data) }
                }

            }

        }
    }
}