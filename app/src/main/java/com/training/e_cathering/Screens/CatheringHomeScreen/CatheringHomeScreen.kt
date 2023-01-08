package com.training.e_cathering.Screens.CatheringHomeScreen
import android.content.Context
import android.os.Bundle

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.training.e_cathering.Models.GridModal
import com.training.e_cathering.Navigation.NavigationEnum
import com.training.e_cathering.R
import java.util.*




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CatheringHomeScreen(navController: NavController) {
    lateinit var choiceList: List<GridModal>
    choiceList = ArrayList<GridModal>()

    choiceList = choiceList + GridModal("List Pesanan", R.drawable.orderlist,
        NavigationEnum.OrderListScreenActivity.name)
    choiceList = choiceList + GridModal("Buat Produk", R.drawable.createproduct,NavigationEnum.CreateProductActivity.name)
    choiceList = choiceList + GridModal("Manajemen Produk", R.drawable.managefood,"adds")
    choiceList = choiceList + GridModal("Profile", R.drawable.ic_baseline_account_box_24, NavigationEnum.CatheringProfileScreenActivity.name)
    choiceList = choiceList + GridModal("Logout", R.drawable.ic_baseline_logout_24,"logout")
    val mContext = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()){
        Box(
            Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.primary)
                .padding(top = 10.dp)){
            Column(modifier = Modifier.padding(horizontal = 10.dp), verticalArrangement = Arrangement.Center){
                Text("Welcome, user !", color=Color.White)
                Text("Home Cathering", color=Color.White, fontWeight = FontWeight.Bold, fontSize = 30.sp,modifier = Modifier.padding(bottom = 16.dp))


            }

        }
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Text("Pilihan Menu", fontWeight = FontWeight.Bold, fontSize = 25.sp,modifier = Modifier.padding(top = 16.dp))
            //list pesanan, buat produk, manajemen produk,profile, logout


    LazyVerticalGrid(

        columns = GridCells.Fixed(2),

        modifier = Modifier.padding(10.dp)
    ) {

        items(choiceList.size) {

            Card(

                onClick = {
                    navController.navigate(choiceList[it].navigation)
                    Toast.makeText(
                        mContext,
                        choiceList[it].iconName + " selected..",

                        Toast.LENGTH_SHORT
                    ).show()
                },


                modifier = Modifier.padding(8.dp),


                elevation = 6.dp
            ) {

                Column(

                    Modifier
                        .fillMaxSize()
                        .padding(5.dp),

                    // on below line we are adding
                    // horizontal alignment for our column
                    horizontalAlignment = Alignment.CenterHorizontally,

                    // on below line we are adding
                    // vertical arrangement for our column
                    verticalArrangement = Arrangement.Center
                ) {

                    Image(

                        painter = painterResource(id = choiceList[it].iconImg),

                        contentDescription = "Javascript",

                        modifier = Modifier
                            .height(60.dp)
                            .width(60.dp)
                            .padding(5.dp)
                    )

                    // on the below line we are adding a spacer.
                    Spacer(modifier = Modifier.height(9.dp))

                    Text(

                        text = choiceList[it].iconName,

                        modifier = Modifier.padding(4.dp),

                        color = Color.Black
                    )
                }
            }
        }
    }
        }


    }


}