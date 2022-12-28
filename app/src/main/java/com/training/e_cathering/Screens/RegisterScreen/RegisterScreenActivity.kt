package com.training.e_cathering.Screens.RegisterScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.training.e_cathering.Components.SimpleAlertDialog
import com.training.e_cathering.Components.basicInputField
import com.training.e_cathering.Components.passwordInputField
import com.training.e_cathering.Models.User

@Composable
fun RegisterScreenActivity(viewModel: RegisterViewModel) {
    val emailInput = remember{
        mutableStateOf("")
    }

    val passwordInput = remember{
        mutableStateOf("")
    }

    val showAlertDialog = remember{
        mutableStateOf(false)
    }

    val alertDialogMsg = remember{
        mutableStateOf("")
    }

    val loadingProgress = remember {
        mutableStateOf(false)
    }

    val passwordVisible = remember{
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = viewModel.registerStatus.collectAsState(initial = "").value){

        viewModel.registerStatus.collect{
            if(it.data != null){
                if(it.data!!.message == "success"){
                    loadingProgress.value = false
                    alertDialogMsg.value = "Register berhasil"
                    showAlertDialog.value = true
                }else{
                    alertDialogMsg.value = "Error : Please check your email and password"
                    showAlertDialog.value = true
                }
            }

        }

    }

    val closeAlertDialog : () -> Unit = {
        alertDialogMsg.value = ""
        showAlertDialog.value = false
    }

    if(showAlertDialog.value){
        SimpleAlertDialog(title = if(alertDialogMsg.value == "Register berhasil")  "success" else "Error", message = alertDialogMsg.value, onDismissRequest = {
            closeAlertDialog()
            loadingProgress.value = false
        }) {
            closeAlertDialog()
            loadingProgress.value = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp), verticalArrangement = Arrangement.Center) {

        Text("REGISTER", modifier=Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 25.sp)
        basicInputField(label = "Email", inputValue = emailInput.value){
            emailInput.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        passwordInputField(passwordVisible = passwordVisible.value, inputValue = passwordInput.value, pwdIcon = {passwordVisible.value = !passwordVisible.value}){
            passwordInput.value = it
        }
        Spacer(modifier = Modifier.height(15.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
            Button(onClick = {
                loadingProgress.value = true
                val user = User(emailInput.value, passwordInput.value, "user")
                viewModel.register(user)
            }) {
                if(loadingProgress.value){
                    CircularProgressIndicator(color = Color.White)
                }else{
                    Text("REGISTER")
                }
            }
        }

    }
}

