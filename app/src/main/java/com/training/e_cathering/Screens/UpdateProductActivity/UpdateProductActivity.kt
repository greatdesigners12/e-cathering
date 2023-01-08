package com.training.e_cathering.Screens.UpdateProductActivity

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun UpdateProductActivity(navController: NavController, product_id : String, updateProductViewModel: UpdateProductViewModel) {
    Text(product_id)
}