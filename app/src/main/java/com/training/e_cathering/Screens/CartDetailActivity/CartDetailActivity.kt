package com.training.e_cathering.Screens.CartDetailActivity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.training.e_cathering.Components.CustomAlertDialog
import com.training.e_cathering.Components.textInputField
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.CartRelationshipWithUserInformation
import com.training.e_cathering.Models.CartWithRelationship
import com.training.e_cathering.Models.TransactionProduct
import com.training.e_cathering.Models.TransactionRequest
import com.training.e_cathering.Navigation.NavigationEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.suspendCoroutine

private lateinit var fusedLocationClient: FusedLocationProviderClient
private val _data = MutableSharedFlow<String?>()
private val data = _data.asSharedFlow()

suspend fun getLastLocation(context: Context){
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        _data.emit(null)
    }

    fusedLocationClient?.lastLocation!!.addOnCompleteListener(context as Activity) { task ->
        if (task.isSuccessful && task.result != null) {
            val geoCoder = Geocoder(context, Locale.getDefault())
            val addresses = geoCoder.getFromLocation(
                task.result.latitude,
                task.result.longitude,
                1
            )
            var fullAddress = ""
            fullAddress += if (addresses[0].locality != null) addresses[0].locality + " " else ""
            fullAddress += if (addresses[0].countryName != null) addresses[0].countryName + " " else ""
            fullAddress += if (addresses[0].postalCode != null) ", " + addresses[0].postalCode else ""

            GlobalScope.launch(Dispatchers.IO) {
                _data.emit(fullAddress)
            }


        } else {
            Log.w(ContentValues.TAG, "getLastLocation:exception ${task.exception?.message}")

            GlobalScope.launch(Dispatchers.IO) {
                _data.emit(null)
            }
        }

    }

}

@Composable
fun CartDetailActivity(navController: NavController, catheringId : String, cartDetailViewModel: CartDetailViewModel) {



    val cartProducts = remember{
        mutableStateListOf<CartRelationshipWithUserInformation>()
    }

    val transactionProducts = remember{
        mutableStateListOf<TransactionProduct>()
    }

    val cartsId = remember{
        mutableStateListOf<Int>()
    }

    val context = LocalContext.current
    val dataStore = DataStoreInstance(context)

    val fullAddress = remember{mutableStateOf<String?>(null)}

    val openEditAddressDialog = remember{
        mutableStateOf(false)
    }



    val tempAddressData = remember{
        mutableStateOf("")
    }

    var totalPrice = remember{
        mutableStateOf(0)
    }

    var shippingPrice = remember{
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = true){
        cartDetailViewModel.getAllCartProducts(dataStore.getUserId, catheringId, dataStore.getToken)
        getLastLocation(context)

    }
    LaunchedEffect(key1 = cartDetailViewModel.createStatus.collectAsState(initial = "").value){
        cartDetailViewModel.createStatus.collect{
            if(it != null){
                if(it.status == "success"){
                    navController.navigate(NavigationEnum.HistoryScreenActivity.name) {
                        popUpTo(NavigationEnum.HomeScreenActivity.name)
                    }

                }
            }

        }
    }

    LaunchedEffect(key1 = data.collectAsState(initial = "").value){
        data.collect{
            if(it != null){
                fullAddress.value = it
                tempAddressData.value = it
            }

        }
    }

    LaunchedEffect(key1 = cartDetailViewModel.cartProducts.collectAsState(initial = "").value){
        cartDetailViewModel.cartProducts.collect{
            cartProducts.clear()
            for(data in it){
                totalPrice.value += data.Product.harga
                cartsId.add(data.id)
                cartProducts.add(data)
                transactionProducts.add(TransactionProduct(data.Product.nama, data.Product.harga, 1))
            }

        }
    }

    if(openEditAddressDialog.value){
        CustomAlertDialog(title = "Edit Address", onDismiss = { openEditAddressDialog.value = false }) {


            Column() {
                fullAddress.value?.let { textInputField("Address", tempAddressData.value ){
                    tempAddressData.value = it
                    Log.d(TAG, "CartDetailActivity: ${it}")
                }
                }

                Button(onClick = {
                    fullAddress.value = tempAddressData.value
                    openEditAddressDialog.value = false
                }) {
                    Text("Update Address")
                }
            }


        }
    }

    if(cartProducts.size != 0 && fullAddress.value != null){
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)){
            item {
                Text("Alamat", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Row(){
                    Text(fullAddress.value!!)
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Icon",
                        tint = Color.White
                    )
                }

                Button(onClick = { openEditAddressDialog.value = true}) {
                    Text("Edit Address")
                }


            }

            items(items=cartProducts){item ->
                Spacer(modifier = Modifier.height(10.dp))
                Card(modifier = Modifier.height(100.dp)) {
                    Row(modifier = Modifier.fillMaxSize()){
                        Box(
                            Modifier
                                .fillMaxWidth(0.3f)
                                .fillMaxHeight()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(item.Product.image)
                                    .crossfade(true)
                                    .build(),
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentDescription = "Product image",
                                contentScale = ContentScale.Crop,
                                )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            Modifier
                                .fillMaxWidth(0.7f)
                                .fillMaxHeight()) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Text(text = item.Product.nama)
                                Text(text = "Rp. " + item.Product.harga.toString())
                                Row(modifier = Modifier.height(30.dp)){
                                    Box(modifier = Modifier
                                        .fillMaxHeight()
                                        .clip(shape = RoundedCornerShape(size = 12.dp))
                                        .background(MaterialTheme.colors.primary)
                                        .padding(5.dp)
                                    ) {
                                        Text("One time", color = Color.White)
                                    }
                                    if(item.Product.type == "daily"){
                                        Box(modifier = Modifier
                                            .fillMaxHeight()
                                            .background(MaterialTheme.colors.primary)) {
                                            Text("Daily")
                                        }
                                    }

                                }

                                Row(modifier = Modifier.height(20.dp)){
                                    Icon(Icons.Rounded.Remove, "menu")
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(text = "1")
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Icon(Icons.Rounded.Add, "menu")
                                }
                            }


                        }
                    }
                }
            }
            item{
                Column(){
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("Shipping price", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(shippingPrice.value.toString(), fontSize = 17.sp)
                    Text("Total price", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text("Rp. " + totalPrice.value.toString(), fontSize = 17.sp)
                }

            }
            item{
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp), contentAlignment = Alignment.BottomCenter){
                    ExtendedFloatingActionButton(
                        // on below line we are setting text for our fab
                        text = { Text(text = "Order Now") },
                        // on below line we are adding click listener.
                        onClick = {
                            cartDetailViewModel.createTransaction(TransactionRequest(products = transactionProducts, carts_id=cartsId, cathering_id = catheringId.toInt(), user_id = 0, shipping_price = shippingPrice.value, total_price = totalPrice.value, full_address = fullAddress.value!!), dataStore.getUserId, dataStore.getToken)
                        },
                        // on below line adding
                        // a background color.
                        backgroundColor = MaterialTheme.colors.primary,
                        // on below line we are
                        // adding a content color.
                        contentColor = Color.White,
                        // on below line we are
                        // adding icon for our fab
                        icon = { Icon(Icons.Filled.Add, "") }
                    )
                }
            }
        }
    }else{
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }
}

