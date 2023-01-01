package com.training.e_cathering.Screens.CatheringListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Models.CatheringWithRating
import com.training.e_cathering.Models.Product
import com.training.e_cathering.R
import kotlinx.coroutines.flow.collect
import kotlin.math.roundToInt


@Composable
fun CatheringListScreen(genre : String,viewModel: CatheringListViewModel) {
    val catheringList = remember{
        mutableStateListOf<CatheringWithRating>()
    }

    val dataStore = DataStoreInstance(LocalContext.current)

    LaunchedEffect(key1 = true){
        viewModel.getCatheringBasedOnGenre(genre, dataStore.getToken)
    }

    LaunchedEffect(key1 = viewModel.catheringList.collectAsState(initial = "").value){
        viewModel.catheringList.collect{
            catheringList.addAll(it.data)
        }
    }
    
    LazyColumn(modifier = Modifier.padding(10.dp)){
        
        items(items=catheringList){cathering ->
            CatheringCardHorizontal(cathering = cathering){
                
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun CatheringCardHorizontal(cathering: CatheringWithRating, onClick : (Product) -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()) {
        Row() {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cathering.imageLogo)
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
                Text(text = cathering.nama, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(5.dp))
                Row(){
                    Image(painter = painterResource(id = R.drawable.ic_baseline_star), contentDescription = "")
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(cathering.average_rating.roundToInt().toString())
                }
                Spacer(Modifier.height(5.dp))
                Text(cathering.deskripsi, fontSize = 10.sp)

            }
        }
    }
}