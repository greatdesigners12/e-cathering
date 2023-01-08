package com.training.e_cathering.Screens.OrderDetailScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.training.e_cathering.Components.basicInputField
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderDetailScreen(navController: NavController, id : String, viewModel: OrderDetailViewModel) {



    val inputStatus = remember{
        mutableStateOf("")
    }
    val curData = remember{
        mutableStateOf<TransactionGroup?>(null)
    }
    val productList = remember{
        mutableStateListOf<TransactionProduct>()
    }
    val contextForToast = LocalContext.current.applicationContext

    val listItems = arrayOf("Terbayar", "Dalam Pengantaran", "Selesai")

    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val dataStore = DataStoreInstance(LocalContext.current)

    LaunchedEffect(key1 = true){
        viewModel.getDetailPaidGroup(id, dataStore.getToken)
        viewModel.getGroupbyId(id,dataStore.getToken)

    }
    LaunchedEffect(key1 = viewModel.catheringdata.collectAsState(initial = "").value){
        viewModel.catheringdata.collect{

                curData.value = it.data
                inputStatus.value = curData.value!!.status
                if (inputStatus.value == "Terbayar"){
                    selectedItem ="Terbayar"
                    mutableStateOf(listItems[0])
                }else if(inputStatus.value == "Dalam Pengantaran"){
                    selectedItem ="Dalam Pengantaran"
                    mutableStateOf(listItems[1])
                }else{
                    selectedItem ="Selesai"
                    mutableStateOf(listItems[2])
                }




        }
    }

    LaunchedEffect(key1 = viewModel.transactionList.collectAsState(initial = null).value){
        viewModel.transactionList.collect{
            productList.addAll(it.data)
        }
    }
    val mContext = LocalContext.current
    if (curData!=null){
        Column(modifier = Modifier.fillMaxSize()){
            Box( modifier = Modifier.fillMaxWidth().padding(top=16.dp,bottom=4.dp, start = 16.dp, end = 16.dp)){
                Text(text = "Pesanan Produk",fontWeight = FontWeight.Bold, style= MaterialTheme.typography.h4,modifier = Modifier.padding(start = 8.dp))
            }
    LazyColumn(modifier = Modifier.padding(10.dp)){

        items(items=productList){productList ->

            CatheringCardHorizontal(cathering = productList) {

            }
            Spacer(modifier = Modifier.height(20.dp))

        }
        item{
            Box( modifier = Modifier.fillMaxWidth().padding(top=16.dp,bottom=4.dp, start = 16.dp, end = 16.dp)){
                Text(text = "Status", style= MaterialTheme.typography.h5)
            }
            Box( ){

                ExposedDropdownMenuBox(
                    modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=80.dp, start = 16.dp, end = 16.dp),
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {

                    // text field
                    TextField(
                        value = selectedItem,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(text = "Order Status", fontSize = 20.sp) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    // menu
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listItems.forEach { selectedOption ->
                            // menu item
                            DropdownMenuItem(onClick = {
                                selectedItem = selectedOption
                                expanded = false
                            }) {
                                Text(text = selectedOption, fontSize = 20.sp)
                            }
                        }
                    }
                }


                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().padding(top = 100.dp, bottom = 64.dp)){
                    Button(onClick = {
                        viewModel.updateTransactionGroup(
                            TransactionGroup(curData.value!!.catheringId,curData.value!!.Cathering, curData.value!!.dateTransaction, curData.value!!.id, curData.value!!.idTransaction, curData.value!!.totalPrice, curData.value!!.shipping_price, curData.value!!.transactionGroupRelation, curData.value!!.full_address, curData.value!!.userId, curData.value!!.User, selectedItem) ,
                            id,dataStore.getToken
                        )

                        Toast.makeText(mContext, "Cathering Berhasil Di Edit", Toast.LENGTH_SHORT).show()

                    }) {
                        Text(text = "Ubah Status", fontSize = 25.sp)

                    }
                }
            }
        }

    }





        }

}
}

@Composable
fun CatheringCardHorizontal(cathering: TransactionProduct, onClick: () -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        ) {
        Row() {

            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(text = "Produk", fontWeight = FontWeight.Medium, fontSize = 22.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(1.dp))
                Text(text = cathering.name, fontWeight = FontWeight.Normal, fontSize = 20.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(2.dp))
                Text(text = "Harga/Produk", fontWeight = FontWeight.Medium, fontSize = 22.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(1.dp))
                Text(cathering.price.toString(), fontWeight = FontWeight.Normal, fontSize = 22.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(2.dp))
                Text(text = "Porsi", fontWeight = FontWeight.Medium, fontSize = 22.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(1.dp))
                Text(cathering.portion.toString(), fontWeight = FontWeight.Normal, fontSize = 22.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(2.dp))
                Text(text = "Jumlah Hari", fontWeight = FontWeight.Medium, fontSize = 22.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(1.dp))
                Text(cathering.time.toString(), fontWeight = FontWeight.Normal, fontSize = 20.sp,modifier = Modifier.padding(start = 8.dp))
                Spacer(Modifier.height(5.dp))



            }
        }
    }
}


