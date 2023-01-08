package com.training.e_cathering.Screens.CatheringProfileScreen

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.annotations.SerializedName
import com.training.e_cathering.Components.ImagePickerView
import com.training.e_cathering.Components.basicInputField
import com.training.e_cathering.Components.textInputField
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.Cathering
import com.training.e_cathering.Models.Product
import com.training.e_cathering.Screens.CatheringDetailScreen.CatheringDetailViewModel
import java.net.URI

@Composable
fun CatheringProfileScreen(viewModel: CatheringProfileViewModel){

    val inputNama = remember{
        mutableStateOf("")
    }
    val inputDeskripsi = remember{
        mutableStateOf("")
    }

    val dataStore = DataStoreInstance(LocalContext.current)
    val curData = remember{
        mutableStateOf<Cathering?>(null)
    }

    LaunchedEffect(key1 = true){
        viewModel.getCProfileById(dataStore.getUserId, dataStore.getToken)
    }

    LaunchedEffect(key1 = viewModel.catheringData.collectAsState(initial = "").value){
        viewModel.catheringData.collect{
            if (it !=null){
                curData.value = it
                inputNama.value = curData.value!!.nama
                inputDeskripsi.value = curData.value!!.deskripsi
            }


        }
    }

    if (curData.value !=null){

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        val mContext = LocalContext.current
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().padding(top=16.dp,bottom=8.dp))
        {
            Text(text = "Profile Cathering", textAlign = TextAlign.Center, style= MaterialTheme.typography.h4)

        }
        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=4.dp, start = 16.dp, end = 16.dp)){
            Text(text = "Logo Toko", style= MaterialTheme.typography.h5)
        }
        //image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(curData.value!!.imageLogo)
                .crossfade(true)
                .build(),
            modifier = Modifier.height(150.dp),
            contentDescription = "Cathering image",
            contentScale = ContentScale.Crop,
        )




        Box( modifier = Modifier.fillMaxWidth().padding(top=16.dp,bottom=4.dp, start = 16.dp, end = 16.dp)){
            Text(text = "Nama Toko", style= MaterialTheme.typography.h5)
        }
        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=8.dp, start = 16.dp, end = 16.dp)){
            basicInputField(label = "Nama Toko", inputValue = inputNama.value){
                inputNama.value=it
            }
        }

        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=4.dp, start = 16.dp, end = 16.dp)){
            Text(text = "Deskripsi Toko", style= MaterialTheme.typography.h5)
        }
        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=8.dp, start = 16.dp, end = 16.dp)) {
            textInputField(label = "Deskripsi Toko", inputValue = inputDeskripsi.value) {
                inputDeskripsi.value = it
            }

        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)){
            Button(onClick = {
                viewModel.updateCatheringProfile(
                    Cathering(inputDeskripsi.value,curData.value!!.id, curData.value!!.imageLogo, curData.value!!.imageMenu, "1",inputNama.value, curData.value!!.tanggalRegister,curData.value!!.userId), dataStore.getUserId,
                    dataStore.getToken
                )


                Toast.makeText(mContext, "Profile Berhasil Di Edit", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = "Ubah Cathering Profile")
            }
        }
    }
}}
