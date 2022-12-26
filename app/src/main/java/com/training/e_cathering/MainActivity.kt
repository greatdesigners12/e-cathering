package com.training.e_cathering

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import com.training.e_cathering.Components.UserPageBottomNavigation
import com.training.e_cathering.Navigation.AppNavigation
import com.training.e_cathering.ui.theme.EcatheringTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EcatheringTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    UserMainScreen()
                }
            }
        }
    }
}

@Composable
fun UserMainScreen(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { UserPageBottomNavigation(navController = navController) }
    ) {

        AppNavigation(navController = navController)
    }
}


