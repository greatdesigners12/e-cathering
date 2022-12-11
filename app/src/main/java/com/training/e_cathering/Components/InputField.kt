package com.training.e_cathering.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun basicInputField(label : String, inputValue : String, keyboardType: KeyboardType = KeyboardType.Text, onValueChanged : (String) -> Unit){
    OutlinedTextField(value = inputValue, onValueChanged, singleLine = true, label= { Text(label) }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = keyboardType), colors = TextFieldDefaults.textFieldColors(
        backgroundColor = MaterialTheme.colors.background
    ))
}