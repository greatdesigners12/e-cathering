package com.training.e_cathering.Screens.HistoryScreenActivity

import android.content.ContentValues.TAG
import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Models.TransactionGroup
import com.training.e_cathering.Navigation.NavigationEnum
import com.training.e_cathering.R
import java.text.DecimalFormat


@Composable
fun HistoryScreenActivity(navController: NavController,viewModel: HistoryTransactionViewModel) {

    val transactionGroups = remember{
        mutableStateListOf<TransactionGroup?>(null)
    }
    val context = LocalContext.current
    val dataStore = DataStoreInstance(context)

    LaunchedEffect(key1 = true){
        viewModel.getAllTransactionGroup(dataStore.getUserId, dataStore.getToken)
    }

    LaunchedEffect(key1 = viewModel.transactionGroups.collectAsState(initial = "").value){
        viewModel.transactionGroups.collect{
            transactionGroups.clear()
            transactionGroups.addAll(it)
        }

    }
    if(transactionGroups != null){
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)){
            items(items=transactionGroups){item ->
                if (item != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    TransactionGroupCard(item){
                        Log.d(TAG, "HistoryScreenActivity: ${item.id}")
                        navController.navigate(NavigationEnum.TransactionDetailActivity.name + "/" + item.id)
                    }
                }
            }
        }
    }else{
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

            CircularProgressIndicator()
        }
    }



}

@Composable
fun TransactionGroupCard(transactionGroup: TransactionGroup, onClick : () -> Unit  ){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .clickable {
            onClick()
        }) {
        Row() {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(transactionGroup.Cathering.imageLogo)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(),
                contentDescription = "Product image",
                contentScale = ContentScale.Crop,

                )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(text = transactionGroup.transactionGroupRelation[0].transactionProduct.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(5.dp))
                if(transactionGroup.transactionGroupRelation.size - 1 >= 0){
                    Text( "+" + (transactionGroup.transactionGroupRelation.size - 1) + " other products")
                }

                Spacer(Modifier.height(5.dp))
                if(transactionGroup.status == "Belum terbayar"){
                    Box(modifier = Modifier
                        .width(120.dp)
                        .padding(10.dp)
                        .background(Color.Red).clip(shape=RoundedCornerShape(10.dp)),  contentAlignment = Alignment.Center) {
                        Text("Belum Terbayar", color = Color.White, textAlign = TextAlign.Center)
                    }
                }else{
                    Box(modifier = Modifier
                        .width(120.dp)
                        .padding(10.dp)
                        .background(Color.Green).clip(shape=RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
                        Text("Terbayar", color = Color.White)
                    }
                }

            }

        }
    }
}