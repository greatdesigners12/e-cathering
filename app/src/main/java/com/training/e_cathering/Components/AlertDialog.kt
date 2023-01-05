package com.training.e_cathering.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleAlertDialog(title : String, message : String, onDismissRequest : () -> Unit, onAction : () -> Unit) {
    val isSuccess = title == "success"
    AlertDialog(title={ Text(title) }, text = { Text(message) }, buttons = {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = onAction) {
                Text(if(isSuccess) "Ok" else "Try again")
            }
        }

    }, onDismissRequest = {onDismissRequest()})
}

@Composable
fun CustomAlertDialog(title : String, onDismiss : () -> Unit, components : @Composable () -> Unit) {
    AlertDialog(title={Text(title)}, modifier = Modifier.fillMaxWidth().height(200.dp) , text = {components()}, buttons = {


    }, onDismissRequest = onDismiss)
}