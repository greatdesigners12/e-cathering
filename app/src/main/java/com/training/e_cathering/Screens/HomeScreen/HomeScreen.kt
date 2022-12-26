package com.training.e_cathering.Screens.HomeScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.training.e_cathering.Components.basicInputField
import com.training.e_cathering.Models.Data

@Composable
fun HomeScreenActivity(homeViewModel : HomeViewModel) {
    val searchInput = remember{
        mutableStateOf("")
    }

    val allCatheringList = remember{
        mutableStateListOf<Data>()
    }
    LaunchedEffect(key1 = true){
        homeViewModel.getAllCatherings()
    }
    LaunchedEffect(key1 = homeViewModel.catheringList.collectAsState(initial = null).value){

            homeViewModel.catheringList.collect{
                if(it.data?.data != null){
                    Log.d(TAG, "HomeScreenActivity: ${it.data?.data!!}")
                    allCatheringList.addAll(it.data?.data!!)
                }

            }


    }


    Column(modifier = Modifier.fillMaxSize()){
        Box(
            Modifier
                .height(120.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.primary)
                .padding(top = 10.dp)){
            Column(modifier = Modifier.padding(horizontal = 10.dp), verticalArrangement = Arrangement.Center){
                Text("Welcome, user !", color=Color.White)
                basicInputField(label = "Search", inputValue = searchInput.value){
                    searchInput.value = it
                }

            }

        }
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Text("Favourite Cathering", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            LazyRow{
                items(items=allCatheringList){item ->
                    CatheringCard(item)
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }


    }

}

@Composable
fun CatheringCard(data : Data){
    Card(modifier = Modifier
        .width(100.dp)
        .height(150.dp)){
        Column(){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.imageLogo)
                    .crossfade(true)
                    .build(),
                modifier = Modifier.height(100.dp),
                contentDescription = "Cathering image",
                contentScale = ContentScale.Crop,
            )
            Text(data.nama, textAlign = TextAlign.Center)
        }

    }
}

