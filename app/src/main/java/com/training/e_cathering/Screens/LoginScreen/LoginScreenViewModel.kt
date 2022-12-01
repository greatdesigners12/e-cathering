package com.training.e_cathering.Screens.LoginScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    fun setupPusher(){
        viewModelScope.launch(Dispatchers.IO){
            val options = PusherOptions()
            options.setCluster("ap1");

            val pusher = Pusher("ca572f0229502915bf53", options)

            pusher.connect(object : ConnectionEventListener {
                override fun onConnectionStateChange(change: ConnectionStateChange) {
                    Log.i("Pusher", "State changed from ${change.previousState} to ${change.currentState}")
                }

                override fun onError(
                    message: String,
                    code: String?,
                    e: Exception
                ) {
                    Log.i("Pusher", "There was a problem connecting! code ($code), message ($message), exception($e)")
                }
            }, ConnectionState.ALL)

            val channel = pusher.subscribe("my-channel")
            channel.bind("my-event") { event ->
                Log.i("Pusher","Received event with data: $event")

            }

        }

    }
}