package com.training.e_cathering.Screens.UpdateProductActivity

import androidx.lifecycle.ViewModel
import com.training.e_cathering.Repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateProductViewModel @Inject constructor(productRepository: ProductRepository) : ViewModel() {

}