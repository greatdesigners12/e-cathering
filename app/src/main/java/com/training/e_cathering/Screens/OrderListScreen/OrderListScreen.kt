package com.training.e_cathering.Screens.OrderListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
fun OrderListScreen(navController: NavController, cathering_id : String, viewModel: OrderListViewModel) {
    //tampilkan semua orderan yang ada yang mana sudah bayar->sedang diantar
    //opsi untuk melihat detail orderan ->tampilan list produk biasa



    val transactionList = remember{
        mutableStateListOf<TransactionGroup>()
    }

    val dataStore = DataStoreInstance(LocalContext.current)

    LaunchedEffect(key1 = true){
        viewModel.getAllPaidGroups(cathering_id, dataStore.getToken)
    }

    LaunchedEffect(key1 = viewModel.transactionList.collectAsState(initial = null).value){
        viewModel.transactionList.collect{
            transactionList.addAll(it.data)
        }
    }


    LazyColumn(modifier = Modifier.padding(10.dp)){

        items(items=transactionList){transactionList ->
            CatheringCardHorizontal(cathering = transactionList) {
                navController.navigate(NavigationEnum.OrderDetailScreenActivity.name + "/" + it.id)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun CatheringCardHorizontal(cathering: TransactionGroup, onClick: (TransactionGroup) -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth().clickable {
            onClick(cathering)
        }) {
        Row() {

            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(text = cathering.status, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(5.dp))
                Text(cathering.full_address, fontWeight =FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(5.dp))
                Text(cathering.totalPrice.toString(), fontWeight =FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(5.dp))
                Text(cathering.dateTransaction, fontWeight =FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(5.dp))


            }
        }
    }
}
