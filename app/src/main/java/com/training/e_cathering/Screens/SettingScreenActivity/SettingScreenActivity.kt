package com.training.e_cathering.Screens.SettingScreenActivity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Navigation.NavigationEnum

@Composable
fun SettingScreenActivity(navController: NavController, settingViewModel: SettingViewModel) {
    val context = LocalContext.current
    val dataStore = DataStoreInstance(context)
    Box(modifier = Modifier.fillMaxSize().padding(10.dp), contentAlignment = Alignment.TopStart) {
        Text( "LOGOUT", modifier = Modifier.clickable {
            settingViewModel.logout(dataStore, navController)
        })
    }
}