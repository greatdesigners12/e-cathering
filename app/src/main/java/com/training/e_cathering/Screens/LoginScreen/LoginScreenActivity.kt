package com.training.e_cathering.Screens.LoginScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.training.e_cathering.Components.SimpleAlertDialog
import com.training.e_cathering.Components.basicInputField
import com.training.e_cathering.Components.passwordInputField
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.User
import com.training.e_cathering.Navigation.NavigationEnum
import kotlinx.coroutines.flow.collect

@Composable
fun LoginScreenActivity(navController: NavController, viewModel : LoginViewModel) {
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

    val dataStore = DataStoreInstance(LocalContext.current)

    LaunchedEffect(key1 = true){
        dataStore.getToken.collect{
            Log.d(TAG, "LoginScreenActivity: ${it}")
            if(it != "" && it != null){
                navController.navigate(NavigationEnum.HomeScreenActivity.name)
            }
        }



    }

    LaunchedEffect(key1 = viewModel.loginStatus.collectAsState(initial = "").value){

        viewModel.loginStatus.collect{
            if(it.data != null){
                if(it.data!!.message == "login berhasil"){
                    if(it.data!!.role == "cathering"){
                        Log.d(TAG, "LoginScreenActivity: this is cathering account")
                    }else if(it.data!!.role == "admin"){
                        
                    }else{
                        alertDialogMsg.value = "Login berhasil"
                        showAlertDialog.value = true
                        viewModel.setCredential(it.data!!, dataStore, navController)
                    }
                    


                }else{
                    alertDialogMsg.value = "Please check your email and password"
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
        SimpleAlertDialog(title = if(alertDialogMsg.value == "Login berhasil")  "success" else "Error", message = alertDialogMsg.value, onDismissRequest = {

            closeAlertDialog()
            loadingProgress.value = false


        }) {

            closeAlertDialog()

            loadingProgress.value = false

        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 10.dp), verticalArrangement = Arrangement.Center) {

        Text("LOGIN", modifier= Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 25.sp)
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
                val user = User(emailInput.value, passwordInput.value)
                viewModel.login(user)
            }) {
                if(loadingProgress.value){
                    CircularProgressIndicator(color = Color.White)
                }else{
                    Text("Login")
                }
            }
        }

    }
}