package com.training.e_cathering.Screens.HomeScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.location.*
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.*
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.training.e_cathering.Components.basicInputField
import com.training.e_cathering.Constant.client_key
import com.training.e_cathering.Constant.midtrans_base_url
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.Category
import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Navigation.NavigationEnum
import java.util.*
import kotlin.collections.ArrayList


@Composable
fun HomeScreenActivity(homeViewModel : HomeViewModel, navController: NavController) {
    val searchInput = remember{
        mutableStateOf("")
    }



    val allCatheringList = remember{
        mutableStateListOf<Cathering>()
    }

    val allCategoryList = remember{
        mutableStateListOf<Category>()
    }

    val dataStore = DataStoreInstance(LocalContext.current)
    val context = LocalContext.current



    LaunchedEffect(key1 = true){
        homeViewModel.getAllCatherings()
        homeViewModel.getAllCategories(dataStore.getToken)
    }
    LaunchedEffect(key1 = homeViewModel.catheringList.collectAsState(initial = null).value){

            homeViewModel.catheringList.collect{
                if(it != null){
                    allCatheringList.clear()
                    allCatheringList.addAll(it)
                }else{
                    allCatheringList.clear()
                }

            }


    }

    LaunchedEffect(key1 = homeViewModel.categoryList.collectAsState(initial = null).value){

        homeViewModel.categoryList.collect{
            if(it != null){
                allCategoryList.clear()
                allCategoryList.addAll(it)
            }else{
                allCategoryList.clear()
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
                    homeViewModel.searchAll(it, dataStore.getToken)
                }

            }

        }
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Text("Favourite Cathering", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            LazyRow{
                items(items=allCatheringList){item ->
                    CatheringCard(item){
                        navController.navigate(NavigationEnum.CatheringDetailActivity.name + "/" + item.id)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
            Text("Category", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            LazyRow{
                items(items=allCategoryList){item ->
                    CategoryCard(item){
                        navController.navigate(NavigationEnum.CatheringListActivity.name + "/" + it)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }


        }


    }

}

@Composable
fun CatheringCard(data : Cathering, cardClick : () -> Unit){
    Card(modifier = Modifier
        .width(100.dp)
        .height(150.dp)
        .clickable {
            cardClick()
        }){
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

    @Composable
    fun CategoryCard(data : Category, onClick : (String) -> Unit){
        Card(modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .clickable {
                onClick(data.namaKategori)
            }){
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.image)
                        .crossfade(true)
                        .build(),
                    modifier = Modifier.height(75.dp),
                    contentDescription = "Category image",
                    contentScale = ContentScale.Fit,
                )
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(data.namaKategori, textAlign = TextAlign.Center)
                }

            }

    }







    }

