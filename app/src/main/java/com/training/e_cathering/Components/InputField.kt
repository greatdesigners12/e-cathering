package com.training.e_cathering.Components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun basicInputField(label : String, inputValue : String, keyboardType: KeyboardType = KeyboardType.Text, onValueChanged : (String) -> Unit){
    OutlinedTextField(value = inputValue, onValueChanged, singleLine = true, label= { Text(label) }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = keyboardType), colors = TextFieldDefaults.textFieldColors(
        backgroundColor = MaterialTheme.colors.background
    ))
}
@Composable
fun textInputField(label : String, inputValue : String, keyboardType: KeyboardType = KeyboardType.Text, onValueChanged : (String) -> Unit){
    OutlinedTextField(value = inputValue, onValueChanged, singleLine = false, label= { Text(label) }, modifier = Modifier.fillMaxWidth().height(150.dp), keyboardOptions = KeyboardOptions(keyboardType = keyboardType), colors = TextFieldDefaults.textFieldColors(
        backgroundColor = MaterialTheme.colors.background
    ))
}
@Composable
fun ImagePickerView(
    modifier: Modifier = Modifier,
    lastSelectedImage: Uri?,
    onSelection: (Uri?) -> Unit
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()) {
        onSelection(it)
    }
    Image(
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .clickable {
                galleryLauncher.launch("image/*")
            },
        painter = rememberImagePainter(lastSelectedImage),
        contentDescription = "Profile Picture",
        contentScale = ContentScale.Crop
    )
}