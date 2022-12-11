package com.training.e_cathering.Screens.CreateProductScreen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.training.e_cathering.Components.ImagePickerView
import com.training.e_cathering.Components.basicInputField
import com.training.e_cathering.Components.textInputField
import com.training.e_cathering.Screens.HomeScreen.HomeScreenActivity


@Composable
fun CreateProductScreen() {
    val inputNamaProduk = remember{
        mutableStateOf("")
    }
    val inputHargaProduk = remember{
        mutableStateOf("")
    }
    val inputDeskripsiProduk = remember{
        mutableStateOf("")
    }
    val pickedImage = remember{mutableStateOf<Uri?>(null)}
    Column(modifier = Modifier.fillMaxSize()) {
        val mContext = LocalContext.current
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().padding(top=16.dp,bottom=8.dp))
        {
            Text(text = "Buat Produk", textAlign = TextAlign.Center, style= MaterialTheme.typography.h4)

        }
        ImagePickerView(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            lastSelectedImage = pickedImage.value,
            onSelection = {
                pickedImage.value = it
            }
        )

        Box( modifier = Modifier.fillMaxWidth().padding(top=16.dp,bottom=4.dp, start = 16.dp, end = 16.dp)) {
            Text(text = "Nama Produk", style = MaterialTheme.typography.h5)
        }
        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=8.dp, start = 16.dp, end = 16.dp)){
            basicInputField(label = "Nama Produk", inputValue = inputNamaProduk.value){
            inputNamaProduk.value=it
            }
        }

        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=4.dp, start = 16.dp, end = 16.dp)) {
            Text(text = "Harga Produk", style = MaterialTheme.typography.h5)
        }
        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=8.dp, start = 16.dp, end = 16.dp)){
            basicInputField(label = "Harga Produk", inputValue = inputHargaProduk.value){
            inputHargaProduk.value=it
            }
        }
        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=4.dp, start = 16.dp, end = 16.dp)) {
            Text(text = "Deskripsi Produk", style = MaterialTheme.typography.h5)
        }
        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=8.dp, start = 16.dp, end = 16.dp)){
            textInputField(label = "Deskripsi Produk", inputValue = inputDeskripsiProduk.value){
            inputDeskripsiProduk.value=it
            }
        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)){
        Button(onClick = {
            Toast.makeText(mContext, "Product Berhasil Dibuat", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Kirim")
        }}
    }
}


@Preview
@Composable
fun preview(){
    CreateProductScreen()

}