package com.training.e_cathering.Screens.CartDetailActivity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.training.e_cathering.Models.CartWithRelationship
import kotlinx.coroutines.flow.collect

@Composable
fun CartDetailActivity(navController: NavController, catheringId : String, cartDetailViewModel: CartDetailViewModel) {

    val cartProducts = remember{
        mutableStateListOf<CartWithRelationship>()
    }

    LaunchedEffect(key1 = cartDetailViewModel.cartProducts.collectAsState(initial = "").value){
        cartDetailViewModel.cartProducts.collect{
            cartProducts.addAll(it)
        }
    }

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)){
        item {
            Text("Alamat", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Row(){

            }
        }
    }
}