package com.training.e_cathering.Screens.SettingScreenActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Navigation.NavigationEnum

@Composable
fun SettingScreenActivity(navController: NavController, settingViewModel: SettingViewModel) {
    val context = LocalContext.current
    val dataStore = DataStoreInstance(context)
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), contentAlignment = Alignment.Center) {
        Column() {
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                navController.navigate(NavigationEnum.ResetPasswordActivity.name)
            }) {
                Text("RESET PASSWORD")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(modifier = Modifier.fillMaxWidth(),onClick = {
                settingViewModel.logout(dataStore, navController)
            }) {
                Text("LOGOUT", color = Color.White)
            }
        }

    }
}