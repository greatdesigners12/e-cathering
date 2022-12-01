package com.training.e_cathering.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.training.e_cathering.Navigation.NavigationEnum
import kotlinx.coroutines.delay

@Composable
fun SplashScreenActivity(navController : NavController) {
    LaunchedEffect(true){
        delay(3000)
        navController?.popBackStack()
        navController?.navigate(NavigationEnum.LoginScreenActivity.name)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text("This is splashscreen")
    }

}