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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun basicInputField(label : String, inputValue : String, keyboardType: KeyboardType = KeyboardType.Text, onValueChanged : (String) -> Unit){
    OutlinedTextField(value = inputValue, onValueChanged, singleLine = true, label= { Text(label) }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = keyboardType), colors = TextFieldDefaults.textFieldColors(
        backgroundColor = MaterialTheme.colors.background
    ))
}

@Composable
fun textInputField(label : String, inputValue : String, keyboardType: KeyboardType = KeyboardType.Text, onValueChanged : (String) -> Unit){
    OutlinedTextField(value = inputValue, onValueChanged, singleLine = false, label= { Text(label) }, modifier = Modifier.fillMaxWidth().height(100.dp), keyboardOptions = KeyboardOptions(keyboardType = keyboardType), colors = TextFieldDefaults.textFieldColors(
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
        contentScale = ContentScale.Crop)}


@Composable
fun passwordInputField(inputValue : String, passwordVisible : Boolean, pwdIcon : () -> Unit, onValueChanged : (String) -> Unit){
    OutlinedTextField(value = inputValue, onValueChanged, modifier = Modifier.fillMaxWidth(), singleLine = true, label= {Text("Password")},
        visualTransformation = if(passwordVisible) VisualTransformation.None  else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = pwdIcon) {
                Icon(imageVector = if(passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff, contentDescription = "")

            }
        }

    )
}