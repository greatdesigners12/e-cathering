package com.training.e_cathering.Screens.CatheringDetailScreen


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.training.e_cathering.Components.basicInputField
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.*
import com.training.e_cathering.Navigation.NavigationEnum
import kotlinx.coroutines.launch


@Composable
fun productCardHorizontal(product: Product, buttonText : String, onCardClick : (Product) -> Unit = {}, colorButton : Color = MaterialTheme.colors.primary,onClick : (Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth().clickable {
                onCardClick(product)
            }
            .padding(horizontal = 10.dp)
            .height(75.dp)
    ) {
        Row() {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .fillMaxHeight(),
                contentDescription = "Product image",
                contentScale = ContentScale.Crop,

                )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(bottom = 5.dp)) {
                Text(text = product.nama, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(2.dp))
                Text("Rp. " + product.harga.toString(), fontSize = 15.sp, color = MaterialTheme.colors.primary)
                Spacer(Modifier.height(2.dp))
                Text(product.deskripsi, fontSize = 10.sp)

            }
            Box(
                Modifier
                    .fillMaxSize()
                    .background(colorButton)
                    .clickable {
                        onClick(product)
                    }, contentAlignment = Alignment.Center
            ) {
                Text(buttonText, fontSize = 20.sp, color = Color.White)
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CatheringDetailScreen(navController: NavController, id : String, viewModel: CatheringDetailViewModel) {
    val curData = remember{
        mutableStateOf<Cathering?>(null)
    }
    
    val productsData = remember{
        mutableStateOf<ProductsWithCartChecker?>(null)
    }

    val dataStore = DataStoreInstance(LocalContext.current)


    val cartData = remember{
        mutableStateListOf<Int>()
    }
    val orderPrice = remember{
        mutableStateOf("")
    }

    val foodAvailability = remember{
        mutableStateOf("")
    }
    LaunchedEffect(key1 = true){
        viewModel.getCatheringById(id, dataStore.getToken)
        viewModel.getAllProductsWithCartChecker(dataStore.getUserId, orderPrice.value, foodAvailability.value, id,  dataStore.getToken)
    }

    LaunchedEffect(key1 = viewModel.catheringData.collectAsState(initial = "").value){
        viewModel.catheringData.collect{

            curData.value = it

        }
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    val search = remember{
        mutableStateOf("")
    }



    LaunchedEffect(key1 = viewModel.productData.collectAsState(initial = "").value){
        viewModel.productData.collect{

            productsData.value = it
            cartData.clear()
            if(it.carts != null){
                cartData.addAll(it.carts)

            }else{
                cartData.clear()
            }
        }
    }

    LaunchedEffect(key1 = viewModel.addCartStatus.collectAsState(initial = "").value){
        viewModel.addCartStatus.collect{
            if(it != null){
                if(it.status == "success"){
                    viewModel.getAllProductsWithCartChecker(dataStore.getUserId, orderPrice.value, foodAvailability.value, id,  dataStore.getToken)
                }
            }

        }


    }

    LaunchedEffect(viewModel.removeCartStatus.collectAsState(initial = "").value){
        viewModel.removeCartStatus.collect{

                if(it.status == "success"){
                    viewModel.getAllProductsWithCartChecker(dataStore.getUserId, orderPrice.value, foodAvailability.value, id,  dataStore.getToken)
                }

        }
    }

    val curContext = LocalContext.current


    if(curData.value != null){
        if(productsData.value?.data?.isNotEmpty() == true){
            ModalBottomSheetLayout(
                sheetState = sheetState,
                sheetContent = { BottomSheet(onSortPriceClick = {
                    if(it.equals("Highest")){
                        orderPrice.value = "h"
                    }else if(it.equals("Lowest")){
                        orderPrice.value = "l"
                    }

                    viewModel.getAllProductsWithCartChecker(dataStore.getUserId, orderPrice.value, foodAvailability.value, id,  dataStore.getToken)
                }){
                    if(it.equals("daily")){
                        foodAvailability.value = "d"
                    }else if(it.equals("one time")){
                        foodAvailability.value = "r"
                    }else{
                        foodAvailability.value = ""
                    }
                    viewModel.getAllProductsWithCartChecker(dataStore.getUserId, orderPrice.value, foodAvailability.value, id,  dataStore.getToken)
                } },
                modifier = Modifier.fillMaxSize()
            ) {

                LazyColumn() {
                    item {
                        Box(
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(curData.value!!.imageLogo)
                                    .crossfade(true)
                                    .build(),
                                modifier = Modifier.height(150.dp),
                                contentDescription = "Cathering image",
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }

                    item {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically){
                            basicInputField("Search Food", inputWidth = 0.9f, search.value) {
                                search.value = it
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        coroutineScope.launch {
                                            sheetState.show()
                                        }

                                    }
                                    .fillMaxHeight(),
                                imageVector = Icons.Filled.FilterList,
                                contentDescription = null
                            )
                        }

                    }

                    item {
                        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    curData.value!!.nama,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp
                                )
                                Button(onClick = {

                                }) {
                                    Text("Chat")
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(curData.value!!.deskripsi)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                    if(productsData.value != null){
                        items(items = productsData.value!!.data) { item ->
                            productCardHorizontal(
                                product = item,
                                if (cartData.contains(item.id?.toInt())) "Added" else "+"
                            ) {
                                if (cartData.contains(item.id?.toInt())) {
                                    viewModel.removeCart(
                                        item.id!!.toInt(),
                                        curData.value!!.id,
                                        dataStore.getUserId,
                                        dataStore.getToken
                                    )
                                } else {

                                    it.id?.let { it1 ->
                                        viewModel.addToCart(
                                            dataStore.getUserId, it.catheringId!!.toInt(),
                                            it1.toInt(), dataStore.getToken
                                        )
                                    }
                                }

                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }else{
                        item{
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                Text("No Product found")
                            }
                        }

                    }

                }
            }

        }

        if(cartData.size != 0){
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp), contentAlignment = Alignment.BottomCenter){
                ExtendedFloatingActionButton(
                    // on below line we are setting text for our fab
                    text = { Text(text = "Pesan Sekarang") },
                    // on below line we are adding click listener.
                    onClick = {
                         navController.navigate(NavigationEnum.CartDetailActivity.name + "/" + id)
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




    }else{
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }
}

@Composable
fun BottomSheet(onSortPriceClick : (String) -> Unit, onFoodAvailabilityClick : (String) -> Unit) {
    val radioOptions = listOf("Lowest", "Highest")
    val typeFoods = listOf("All", "daily", "one time")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val (selectedOptionTypeFood, onOptionSelectedTypeFood) = remember { mutableStateOf(typeFoods[0]) }
    Column(
        modifier = Modifier.padding(32.dp)
    ) {
        Text(
            text = "Filter",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Sort Price to ",
                style = MaterialTheme.typography.body1
            )
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            radioOptions.forEach { text ->
                Row(Modifier.selectable(
                    selected = (text == selectedOption),
                    onClick = {
                        onSortPriceClick(text)
                        onOptionSelected(text)
                    },
                ), verticalAlignment = Alignment.CenterVertically){
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = {
                            onSortPriceClick(text)
                            onOptionSelected(text) },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primary)
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Food Availabilty",
            style = MaterialTheme.typography.body1
        )
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            typeFoods.forEach { text ->
                Row(Modifier.selectable(
                    selected = (text == selectedOptionTypeFood),
                    onClick = {
                        onFoodAvailabilityClick(text)
                        onOptionSelectedTypeFood(text)
                    },
                ), verticalAlignment = Alignment.CenterVertically){
                    RadioButton(
                        selected = (text == selectedOptionTypeFood),
                        onClick = {
                            onFoodAvailabilityClick(text)
                            onOptionSelectedTypeFood(text)
                                  },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primary)
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(50.dp))



    }
}


