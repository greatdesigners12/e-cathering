package com.training.e_cathering.Screens.CreateProductScreen


import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.squaredem.composecalendar.ComposeCalendar
import com.training.e_cathering.Components.ImagePickerView
import com.training.e_cathering.Components.basicInputField
import com.training.e_cathering.Components.textInputField
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.Product
import java.time.LocalDate
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateProductScreen(navController: NavController, id_product : String? = null,viewModel: productViewModel) {
    val inputNamaProduk = remember {
        mutableStateOf("")
    }
    val inputHargaProduk = remember {
        mutableStateOf("")
    }
    val inputDeskripsiProduk = remember {
        mutableStateOf("")
    }
    val pickedImage = remember { mutableStateOf<Uri?>(null) }
    val startDate = remember {
        mutableStateOf<LocalDate?>(null)
    }
    val endDate = remember {
        mutableStateOf<LocalDate?>(null)
    }
    val dataStore = DataStoreInstance(LocalContext.current)
    val mContext = LocalContext.current
// first declare a variable that holds the dialog visibility state
    val showStartDateDialog = rememberSaveable { mutableStateOf(false) }
    val showEndDateDialog = rememberSaveable { mutableStateOf(false) }

    val selectedTime = remember {
        mutableStateListOf(true, false)
    }

    val method = remember{
        mutableStateOf("Create")
    }

    val currentUrlImage = remember{
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        if (id_product != null) {
            method.value = "Update"
            viewModel.getProductById(id_product, dataStore.getToken)
        }

    }

    LaunchedEffect(key1 = viewModel.curProduct.collectAsState(initial = "").value) {
        viewModel.curProduct.collect {
            if (it.data != null) {
                inputNamaProduk.value = it.data.nama
                inputHargaProduk.value = it.data.harga.toString()
                inputDeskripsiProduk.value = it.data.deskripsi
                if (it.data.start_date != null) {
                    Log.d(TAG, "endDate: ${it.data.start_date} ")
                    it.data.start_date = it.data.start_date.replace("Z", "")
                    startDate.value = LocalDateTime.parse(it.data.start_date).toLocalDate()
                    if (startDate.value!!.year == 1) {
                        startDate.value = null
                    } else {
                        startDate.value = startDate.value!!.plusDays(1)
                    }
                }
                if (it.data.due_date != null) {
                    Log.d(TAG, "endDate: ${it.data.due_date} ")
                    it.data.due_date = it.data.due_date.replace("Z", "")

                    endDate.value = LocalDateTime.parse(it.data.due_date).toLocalDate()
                    if (endDate.value!!.year == 1) {
                        endDate.value = null
                    } else {
                        startDate.value = startDate.value!!.plusDays(1)
                    }


                }

                if (it.data.start_date != null || it.data.due_date != null) {
                    selectedTime[0] = false
                    selectedTime[1] = true
                }

                pickedImage.value = Uri.parse(it.data.image)
                currentUrlImage.value = it.data.image
            }
        }
    }



    if (showStartDateDialog.value) {
        ComposeCalendar(
            onDone = { it: LocalDate ->
                // Hide dialog
                startDate.value = it
                showStartDateDialog.value = false

                // Do something with the date
            },
            onDismiss = {
                // Hide dialog
                showStartDateDialog.value = false
            }
        )
    }

    if (showEndDateDialog.value) {
        ComposeCalendar(
            minDate = startDate.value!!,
            onDone = { it: LocalDate ->
                // Hide dialog
                endDate.value = it
                showEndDateDialog.value = false

                // Do something with the date
            },
            onDismiss = {
                // Hide dialog
                showEndDateDialog.value = false
            }
        )
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {


        item {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )
            {
                Text(
                    text = "${method.value} Produk",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4
                )

            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                ImagePickerView(

                    lastSelectedImage = pickedImage.value,
                    onSelection = {
                        pickedImage.value = it
                    }
                )
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(text = "Nama Produk", style = MaterialTheme.typography.h5)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            ) {
                basicInputField(label = "Nama Produk", inputValue = inputNamaProduk.value) {
                    inputNamaProduk.value = it
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(text = "Harga Produk", style = MaterialTheme.typography.h5)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            ) {
                basicInputField(
                    label = "Harga Produk",
                    inputValue = inputHargaProduk.value,
                    keyboardType = KeyboardType.Number
                ) {
                    inputHargaProduk.value = it
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(text = "Deskripsi Produk", style = MaterialTheme.typography.h5)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            ) {
                textInputField(
                    label = "Deskripsi Produk",
                    inputValue = inputDeskripsiProduk.value
                ) {
                    inputDeskripsiProduk.value = it
                }
            }


            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(text = "Ketersediaan", style = MaterialTheme.typography.h5)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            ) {
                Box(
                    Modifier
                        .background(if (selectedTime[0]) MaterialTheme.colors.primary else Color.White)
                        .clickable {
                            startDate.value = null
                            endDate.value = null
                            selectedTime.set(0, true)
                            selectedTime.set(1, false)
                        }) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "Daily",
                        color = if (selectedTime[0]) Color.White else Color.Black
                    )
                }

                Box(
                    Modifier
                        .background(if (selectedTime[1]) MaterialTheme.colors.primary else Color.White)
                        .clickable {
                            selectedTime.set(1, true)
                            selectedTime.set(0, false)
                        }) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "Spesific time range",
                        color = if (selectedTime[1]) Color.White else Color.Black
                    )
                }
            }
            if (selectedTime[1]) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(text = "Start Date", style = MaterialTheme.typography.h5)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                ) {
                    if (startDate.value != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = startDate.value.toString())
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(onClick = {
                                showStartDateDialog.value = true
                            }) {
                                Text("Edit Start Date")
                            }
                        }
                    } else {
                        Button(onClick = {

                            showStartDateDialog.value = true
                        }) {
                            Text("Pick Start Date")
                        }
                    }

                }
                if(startDate.value != null){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 0.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text(text = "End Date", style = MaterialTheme.typography.h5)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                    ) {

                        if (endDate.value != null) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = endDate.value.toString())
                                Spacer(modifier = Modifier.width(10.dp))
                                Button(onClick = {
                                    showEndDateDialog.value = true
                                }) {
                                    Text("Edit End Date")
                                }
                            }
                        } else {
                            Button(onClick = {
                                showEndDateDialog.value = true
                            }) {
                                Text("Pick End Date")
                            }
                        }

                    }

                }
                }




            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 20.dp, start = 10.dp, end = 10.dp)
                    .clickable {
                        if (method.value == "Create") {
                            pickedImage.value?.let {
                                viewModel.createProduct(
                                    Product(
                                        null,
                                        "2",
                                        inputDeskripsiProduk.value,
                                        inputHargaProduk.value.toInt(),
                                        "daily",
                                        "",
                                        inputNamaProduk.value,
                                        startDate.value.toString(),
                                        endDate.value.toString()
                                    ), dataStore.getToken,
                                    it
                                )
                            }
                        } else {
                            pickedImage.value?.let {
                                viewModel.updateProduct(
                                    id_product!!,
                                    Product(
                                        null,
                                        "2",
                                        inputDeskripsiProduk.value,
                                        inputHargaProduk.value.toInt(),
                                        if (selectedTime[0]) "daily" else "reccuring",
                                        "",
                                        inputNamaProduk.value,
                                        startDate.value.toString(),
                                        endDate.value.toString()
                                    ), dataStore.getToken,
                                    it
                                )
                            }
                        }
                    }
            ) {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    Log.d(TAG, "CreateProductScreen: broooo")
                    if(method.value == "Create"){
                        pickedImage.value?.let {
                            Log.d(TAG, "CreateProductScreen: gg")

                            viewModel.createProduct(
                                Product(
                                    null,
                                    "2",
                                    inputDeskripsiProduk.value,
                                    inputHargaProduk.value.toInt(),
                                    "daily",
                                    "",
                                    inputNamaProduk.value,
                                    startDate.value.toString(),
                                    endDate.value.toString()
                                ), dataStore.getToken,
                                it
                            )
                        }
                    }else{
                        pickedImage.value?.let {
                            Log.d(TAG, "CreateProductScreen: agg")
                            viewModel.updateProduct(
                                id_product!!,
                                Product(
                                    null,
                                    null,
                                    inputDeskripsiProduk.value,
                                    inputHargaProduk.value.toInt(),
                                    if(selectedTime[0]) "daily" else "reccuring",
                                    currentUrlImage.value,
                                    inputNamaProduk.value,
                                    startDate.value.toString() + "T00:00:00Z",
                                    endDate.value.toString() + "T00:00:00Z"
                                ), dataStore.getToken,
                                it
                            )
                        }
                    }


                }) {
                    Text(text = "Kirim")
                }
            }
        }

    }

}

