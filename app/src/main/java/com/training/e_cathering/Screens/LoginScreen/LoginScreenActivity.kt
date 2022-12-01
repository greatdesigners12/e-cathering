package com.training.e_cathering.Screens.LoginScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange

@Composable
fun LoginScreenActivity(navController: NavController, loginViewModel : LoginScreenViewModel = viewModel()) {
    LaunchedEffect(key1 = true ){
        loginViewModel.setupPusher()
    }
    Column(){
        Text("This is login screen")
    }
}