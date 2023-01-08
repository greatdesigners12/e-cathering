package com.training.e_cathering.Screens.OrderListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.CatheringWithRating
import com.training.e_cathering.Models.TransactionGroup
import com.training.e_cathering.Navigation.NavigationEnum
import com.training.e_cathering.R
import java.text.DecimalFormat

@Composable
fun OrderListScreen(navController: NavController, user_id : String, viewModel: OrderListViewModel) {
    //tampilkan semua orderan yang ada yang mana sudah bayar->sedang diantar
    //opsi untuk melihat detail orderan ->tampilan list produk biasa



    val transactionList = remember{
        mutableStateListOf<TransactionGroup>()
    }

    val dataStore = DataStoreInstance(LocalContext.current)

    LaunchedEffect(key1 = true){
        viewModel.getAllPaidGroups(dataStore.getUserId, dataStore.getToken)
    }

    LaunchedEffect(key1 = viewModel.transactionList.collectAsState(initial = null).value){
        viewModel.transactionList.collect{
            transactionList.addAll(it.data)
        }
    }

    Column(modifier = Modifier.fillMaxSize()){
        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)){
            Text(text = "List Pesanan",fontWeight = FontWeight.Bold, style= MaterialTheme.typography.h4,modifier = Modifier.padding(start = 8.dp))
        }

    LazyColumn(modifier = Modifier.padding(10.dp)){

        items(items=transactionList){transactionList ->
            CatheringCardHorizontal(cathering = transactionList) {
                navController.navigate(NavigationEnum.OrderDetailScreenActivity.name + "/" + it.id)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }}
}

@Composable
fun CatheringCardHorizontal(cathering: TransactionGroup, onClick: (TransactionGroup) -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onClick(cathering)
        }) {
        Row() {

            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(text = "ID Pesanan", fontWeight = FontWeight.Medium, fontSize = 22.sp, color = Color(
                    0xFF3B8637
                ),modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(1.dp))
                Text(text = cathering.idTransaction, fontWeight = FontWeight.Normal,color = Color(
                    0xFF368131
                ), fontSize = 20.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(2.dp))
                Text(text = "Status Pesanan", fontWeight = FontWeight.Medium, fontSize = 22.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(1.dp))
                Text(text = cathering.status, fontWeight = FontWeight.Normal, fontSize = 20.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(2.dp))
                Text(text = "Alamat", fontWeight = FontWeight.Medium, fontSize = 22.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(1.dp))
                Text(cathering.full_address.toString(), fontWeight = FontWeight.Normal, fontSize = 22.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(2.dp))
                Text(text = "Total Uang", fontWeight = FontWeight.Medium, fontSize = 22.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(1.dp))
                Text(cathering.totalPrice.toString(), fontWeight = FontWeight.Normal, fontSize = 20.sp,modifier = Modifier.padding(start = 8.dp))
                Text(text = "Mulai Tanggal", fontWeight = FontWeight.Medium, fontSize = 22.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(1.dp))
                Text(cathering.dateTransaction.toString(), fontWeight = FontWeight.Normal, fontSize = 20.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(5.dp))


            }
        }
    }
}
