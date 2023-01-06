package com.training.e_cathering.Screens.ResetPasswordScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.training.e_cathering.Components.SimpleAlertDialog
import com.training.e_cathering.Components.passwordInputField
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Navigation.NavigationEnum
import kotlinx.coroutines.flow.collect

@Composable
fun ResetPasswordActivity(navController: NavController ,viewModel: ResetPasswordViewModel) {
    val oldPassword = remember{
        mutableStateOf("")
    }

    val oldPasswordVsble = remember{
        mutableStateOf(false)
    }

    val newPassword = remember{
        mutableStateOf("")
    }

    val newPasswordVsble = remember{
        mutableStateOf(false)
    }

    val newPasswordConfirm = remember{
        mutableStateOf("")
    }

    val newPasswordConfirmVsble = remember{
        mutableStateOf(false)
    }

    val showAlertDialog = remember{
        mutableStateOf(false)
    }

    val alertDialogMsg = remember{
        mutableStateOf("")
    }
    val context = LocalContext.current
    val dataStore = DataStoreInstance(context)

    LaunchedEffect(key1 = viewModel.resetPasswordStatus.collectAsState(initial = "").value){
        viewModel.resetPasswordStatus.collect{
            if(it.status == "200"){
                alertDialogMsg.value = "Reset password succeed"
                showAlertDialog.value = true
            }else if(it.status == "400"){
                alertDialogMsg.value = "Reset password failed, please check your input"
                showAlertDialog.value = true
            }
        }
    }

    val closeAlertDialog : () -> Unit = {
        if(alertDialogMsg.value == "Reset password succeed"){
            navController.navigate(NavigationEnum.SettingScreenActivity.name) {
                popUpTo(NavigationEnum.SettingScreenActivity.name)
            }
        }else{
            alertDialogMsg.value = ""
            showAlertDialog.value = false
        }

    }

    if(showAlertDialog.value){
        SimpleAlertDialog(title = if(alertDialogMsg.value == "Reset password succeed")  "success" else "Error", message = alertDialogMsg.value, onDismissRequest = {

            closeAlertDialog()



        }) {

            closeAlertDialog()



        }
    }



    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Reset Password", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        passwordInputField("Old Password" ,passwordVisible = oldPasswordVsble.value, inputValue = oldPassword.value, pwdIcon = {oldPasswordVsble.value = !oldPasswordVsble.value}){
            oldPassword.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        passwordInputField("New Password" ,passwordVisible = newPasswordVsble.value, inputValue = newPassword.value, pwdIcon = {newPasswordVsble.value = !newPasswordVsble.value}){
            newPassword.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        passwordInputField("Confirm New Password", passwordVisible = newPasswordConfirmVsble.value, inputValue = newPasswordConfirm.value, pwdIcon = {newPasswordConfirmVsble.value = !newPasswordConfirmVsble.value}){
            newPasswordConfirm.value = it
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            var errorCounter = 0
            if(newPassword.value != newPasswordConfirm.value){
                errorCounter += 1
            }

            if(errorCounter == 0){
                viewModel.resetPassword(oldPassword.value ,newPassword.value, dataStore.getUserId)
            }else{
                alertDialogMsg.value = "Reset password failed, please check your input"
                showAlertDialog.value = true
            }

        }) {
            Text("Update Password")
        }
    }
}