package com.training.e_cathering.Screens.ProductDetailScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.Product

@Composable
fun ProductDetailScreen(navController: NavController, id : String, viewModel: ProductDetailViewModel) {
    val curData = remember{
        mutableStateOf<Product?>(null)
    }

    val dataStore = DataStoreInstance(LocalContext.current)

    LaunchedEffect(key1 = true){
        viewModel.getProductDetail(id, dataStore.getToken)
    }

    LaunchedEffect(key1 = viewModel.curData.collectAsState(initial = "").value){
        viewModel.curData.collect{
            curData.value = it
        }
    }


    if(curData.value != null){
        Column() {
            Box(modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(), contentAlignment = Alignment.TopCenter){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(curData.value!!.image)
                        .crossfade(true)
                        .build(),
                    modifier = Modifier.height(150.dp),
                    contentDescription = "Cathering image",
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(curData.value!!.nama, fontWeight = FontWeight.Bold, fontSize = 25.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(curData.value!!.deskripsi)
            Button(onClick = { /*TODO*/ }) {
                Text("Pesan")
            }
            Button(onClick = { /*TODO*/ }) {
                Text("Tambah ke keranjang")
            }
        }
    }else{
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }

}