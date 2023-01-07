package com.training.e_cathering.Screens.CatheringApprovalScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.training.e_cathering.Models.TransactionGroup
import com.training.e_cathering.Navigation.NavigationEnum
import com.training.e_cathering.R
import com.training.e_cathering.Screens.HomeScreen.CatheringCard
import java.text.DecimalFormat

@Composable
fun CatheringApprovalScreen(viewModel: CatheringApprovalViewModel) {

    val allCatheringList = remember{
        mutableStateListOf<Cathering>()
    }



    LaunchedEffect(key1 = true){
        viewModel.getAllCatherings()
    }

    LaunchedEffect(key1 = viewModel.catheringList.collectAsState(initial = null).value){

        viewModel.catheringList.collect{
            if(it != null){
                allCatheringList.clear()
                allCatheringList.addAll(it)
            }else{
                allCatheringList.clear()
            }

        }


    }

    Column(modifier = Modifier.fillMaxSize()){
        Box( modifier = Modifier.fillMaxWidth().padding(top=16.dp,bottom=4.dp, start = 16.dp, end = 16.dp)){
            Text(text = "Cathering Admin Approval",fontWeight = FontWeight.Bold, style= MaterialTheme.typography.h4,modifier = Modifier.padding(start = 8.dp))
        }

    LazyColumn(modifier = Modifier.padding(10.dp)){

        items(items=allCatheringList){cathering ->
            CatheringCardHorizontal(cathering = cathering, viewModel){


            }




            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
}

@Composable
fun CatheringCardHorizontal(cathering: Cathering,  viewModel: CatheringApprovalViewModel, cardClick : () -> Unit, ){
    Card(modifier = Modifier
        .fillMaxWidth().clickable{
            cardClick()
        }) {
        val dataStore = DataStoreInstance(LocalContext.current)
        val mContext = LocalContext.current
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



                    if (cathering.isVerified=="1"){
                        Text("Verified", fontSize = 10.sp)
                    }else{
                        Text("UnVerified", fontSize = 10.sp)
                    }

                Spacer(Modifier.height(5.dp))
                Text(cathering.deskripsi, fontSize = 10.sp)
                Spacer(Modifier.height(5.dp))

            }
            if (cathering.isVerified=="1") {

                Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFAD1919)),modifier = Modifier
                    .defaultMinSize(minWidth = 0.dp, minHeight = 1.dp), contentPadding = PaddingValues(14.dp),onClick = {

                    viewModel.updateVerifiedCathering(
                        Cathering(
                            cathering.deskripsi,
                            cathering.id,
                            cathering.imageLogo,
                            cathering.imageMenu,
                            "0",
                            cathering.nama,
                            cathering.tanggalRegister,
                            cathering.userId
                        ), cathering.userId,
                        dataStore.getToken
                    )




                    Toast.makeText(mContext, "Cathering Berhasil Di Edit", Toast.LENGTH_SHORT).show()

                }) {

                    Text(text = "Disapprove", fontSize = 10.sp, color = Color.White)


                }


            }else{
                Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF46A240)),modifier = Modifier
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),contentPadding = PaddingValues(14.dp),onClick = {

                    viewModel.updateVerifiedCathering(
                        Cathering(
                            cathering.deskripsi,
                            cathering.id,
                            cathering.imageLogo,
                            cathering.imageMenu,
                            "1",
                            cathering.nama,
                            cathering.tanggalRegister,
                            cathering.userId
                        ), cathering.userId,
                        dataStore.getToken
                    )




                    Toast.makeText(mContext, "Cathering Berhasil Di Edit", Toast.LENGTH_SHORT).show()

                }) {

                    Text(text = "Approve", fontSize = 10.sp, color = Color.White)


                }
            }
        }
    }
}