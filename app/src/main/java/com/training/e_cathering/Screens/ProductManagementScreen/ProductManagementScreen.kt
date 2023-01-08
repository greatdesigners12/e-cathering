package com.training.e_cathering.Screens.ProductManagementScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.training.e_cathering.Components.CustomAlertDialog
import com.training.e_cathering.Components.basicInputField
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.CatheringWithRating
import com.training.e_cathering.Models.Product
import com.training.e_cathering.Navigation.NavigationEnum
import com.training.e_cathering.Screens.CatheringDetailScreen.productCardHorizontal
import com.training.e_cathering.Screens.CatheringListScreen.CatheringCardHorizontal

@Composable
fun ProductManagementScreen(navController : NavController, viewModel: ProductManagementViewModel) {
    val productList = remember{
        mutableStateListOf<Product>()
    }

    val dataStore = DataStoreInstance(LocalContext.current)

    LaunchedEffect(key1 = true){
        viewModel.getAllProductByUserId(dataStore.getUserId, dataStore.getToken)
    }

    val curProductId = remember{
        mutableStateOf(0)
    }

    val openDeleteConfirm = remember{
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = viewModel.productList.collectAsState(initial = "").value){
        viewModel.productList.collect{
            productList.clear()
            productList.addAll(it.data)
        }
    }



    val search = remember{
        mutableStateOf("")
    }

    if(openDeleteConfirm.value){
        CustomAlertDialog(title = "Confirm Delete Product", onDismiss = { /*TODO*/ }) {
            Column() {
                Text("Are you sure want to delete this product ?")
                Row(){
                    Button(onClick = { openDeleteConfirm.value = false}, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
                        Text("Cancel", color=Color.White)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = {
                        viewModel.deleteProductById(curProductId.value.toString(), dataStore.getUserId, dataStore.getToken)
                        viewModel.getAllProductByUserId(dataStore.getUserId, dataStore.getToken)
                    }) {
                        Text("Yes")
                    }
                }
            }

        }
    }



    LazyColumn(modifier = Modifier.padding(10.dp)) {
        item{
            Column() {
                basicInputField("Search Product", inputValue = search.value){
                    search.value = it
                }

            }


        }
        items(items = productList) { product ->
            Spacer(modifier = Modifier.height(20.dp))
            productCardHorizontal(
                product = product,
                "X",
                onCardClick = {
                    navController.navigate(NavigationEnum.UpdateProductActivity.name + "/" + product.id)

                },
                Color.Red
            ) {
                curProductId.value = it.id!!.toInt()
                openDeleteConfirm.value = true
            }
            }
        }
}
