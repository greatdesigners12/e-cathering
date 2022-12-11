package com.training.e_cathering.Screens.CreateFrozenFoodScreen

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.training.e_cathering.Components.ImagePickerView
import com.training.e_cathering.Components.basicInputField
import com.training.e_cathering.Components.textInputField


@Composable
fun CreateFrozenFoodScreen() {

    val inputNamaProduk = remember{
        mutableStateOf("")
    }
    val inputHargaProduk = remember{
        mutableStateOf("")
    }
    val inputStokProduk = remember{
        mutableStateOf("")
    }
    val inputDeskripsiProduk = remember{
        mutableStateOf("")
    }
    val pickedImage = remember{ mutableStateOf<Uri?>(null) }
    Column(modifier = Modifier.fillMaxSize()) {
        val mContext = LocalContext.current
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().padding(top=16.dp,bottom=8.dp))
        {
            Text(text = "Buat Makanan Beku", textAlign = TextAlign.Center, style= MaterialTheme.typography.h4)

        }
        ImagePickerView(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            lastSelectedImage = pickedImage.value,
            onSelection = {
                pickedImage.value = it
            }
        )


        Box( modifier = Modifier.fillMaxWidth().padding(top=16.dp,bottom=4.dp, start = 16.dp, end = 16.dp)){
            Text(text = "Nama Produk", style= MaterialTheme.typography.h5)
        }
        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=8.dp, start = 16.dp, end = 16.dp)){
            basicInputField(label = "Nama Produk", inputValue = inputNamaProduk.value){
            inputNamaProduk.value=it
            }
        }

        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=4.dp, start = 16.dp, end = 16.dp)){
            Text(text = "Harga Produk", style = MaterialTheme.typography.h5)
        }
        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=8.dp, start = 16.dp, end = 16.dp)){
            basicInputField(label = "Harga Produk", inputValue = inputHargaProduk.value){
            inputHargaProduk.value=it
            }
        }

        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=4.dp, start = 16.dp, end = 16.dp)){
            Text(text = "Stok Produk", style= MaterialTheme.typography.h5)
        }
        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=8.dp, start = 16.dp, end = 16.dp)){
            basicInputField(label = "Stok Produk", inputValue = inputStokProduk.value){
            inputStokProduk.value=it
            }
        }

        Box( modifier = Modifier.fillMaxWidth().padding(top=0.dp,bottom=4.dp, start = 16.dp, end = 16.dp)){
            Text(text = "Deskripsi Produk", style= MaterialTheme.typography.h5)
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
            }
        }
    }
}