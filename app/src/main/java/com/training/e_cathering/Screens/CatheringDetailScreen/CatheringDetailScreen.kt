package com.training.e_cathering.Screens.CatheringDetailScreen

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.*
import com.training.e_cathering.Screens.ProductDetailScreen.ProductDetailViewModel
import com.training.e_cathering.ui.theme.EcatheringTheme

@Composable
fun CatheringDetailScreen(navController: NavController, id : String, viewModel: CatheringDetailViewModel) {
    val curData = remember{
        mutableStateOf<Cathering?>(null)
    }
    
    val productsData = remember{
        mutableStateOf<ProductsWithCartChecker?>(null)
    }

    val dataStore = DataStoreInstance(LocalContext.current)

    val addCartStatus = remember{
        mutableStateOf<SingleResponseData<CartWithRelationship>?>(null)
    }

    val cartData = remember{
        mutableStateListOf<Int>()
    }

    LaunchedEffect(key1 = true){
        viewModel.getCatheringById(id, dataStore.getToken)
        viewModel.getAllProductsWithCartChecker(dataStore.getUserId, id, dataStore.getToken)
    }

    LaunchedEffect(key1 = viewModel.catheringData.collectAsState(initial = "").value){
        viewModel.catheringData.collect{

            curData.value = it

        }
    }

    LaunchedEffect(key1 = viewModel.productData.collectAsState(initial = "").value){
        viewModel.productData.collect{

            productsData.value = it
            cartData.clear()
            if(it.carts != null){
                cartData.addAll(it.carts)

            }else{
                cartData.clear()
            }
        }
    }

    LaunchedEffect(key1 = viewModel.addCartStatus.collectAsState(initial = "").value){
        viewModel.addCartStatus.collect{
            if(it != null){
                if(it.status == "success"){
                    viewModel.getAllProductsWithCartChecker(dataStore.getUserId, id, dataStore.getToken)
                }
            }

        }


    }

    LaunchedEffect(viewModel.removeCartStatus.collectAsState(initial = "").value){
        viewModel.removeCartStatus.collect{
            if(it != null){
                if(it.status == "success"){
                    viewModel.getAllProductsWithCartChecker(dataStore.getUserId, id, dataStore.getToken)
                }
            }
        }
    }

    val curContext = LocalContext.current


    if(curData.value != null ){
        if(productsData.value?.data?.isNotEmpty() == true){
            LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)){
                item{
                    Box(modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()){
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(curData.value!!.imageLogo)
                                .crossfade(true)
                                .build(),
                            modifier = Modifier.height(150.dp),
                            contentDescription = "Cathering image",
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(curData.value!!.nama, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(curData.value!!.deskripsi)
                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(items= productsData.value!!.data) { item ->
                    productCardHorizontal(product = item, if(cartData.contains(item.id?.toInt())) "Added" else "+"){
                        if(cartData.contains(item.id?.toInt())){
                            viewModel.removeCart(item.id!!.toInt(), curData.value!!.id, dataStore.getUserId, dataStore.getToken)
                        }else{
                            it.id?.let { it1 ->
                                viewModel.addToCart(dataStore.getUserId, it.catheringId.toInt(),
                                    it1.toInt(), dataStore.getToken)
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

        }

        if(cartData.size != 0){
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp), contentAlignment = Alignment.BottomCenter){
                ExtendedFloatingActionButton(
                    // on below line we are setting text for our fab
                    text = { Text(text = "Pesan Sekarang") },
                    // on below line we are adding click listener.
                    onClick = {
                        Toast.makeText(curContext, "Extended Floating Action Button", Toast.LENGTH_SHORT).show()
                    },
                    // on below line adding
                    // a background color.
                    backgroundColor = MaterialTheme.colors.primary,
                    // on below line we are
                    // adding a content color.
                    contentColor = Color.White,
                    // on below line we are
                    // adding icon for our fab
                    icon = { Icon(Icons.Filled.Add, "") }
                )
            }
        }




    }else{
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }
}

@Composable
fun productCardHorizontal(product: Product, buttonText : String, onClick : (Product) -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(75.dp)) {
        Row() {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .fillMaxHeight(),
                contentDescription = "Product image",
                contentScale = ContentScale.Crop,
                
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(text = product.nama, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(10.dp))
                Text(product.deskripsi, fontSize = 18.sp)
                
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary)
                    .clickable {
                        onClick(product)
                    }, contentAlignment = Alignment.Center){
                Text(buttonText, fontSize = 20.sp, color = Color.White)
            }
        }
    }
}

