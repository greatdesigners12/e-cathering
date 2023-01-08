package com.training.e_cathering.Screens.LoginScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.Auth
import com.training.e_cathering.Models.LoginJwtToken
import com.training.e_cathering.Models.User
import com.training.e_cathering.Navigation.NavigationEnum
import com.training.e_cathering.Repositories.AuthRepository
import com.training.e_cathering.Utils.DataAPIWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository)  : ViewModel()  {
    private val _loginStatus = MutableSharedFlow<DataAPIWrapper<LoginJwtToken, Boolean, Exception>>()
    val loginStatus = _loginStatus.asSharedFlow()
    fun login(user : User){
        viewModelScope.launch(Dispatchers.IO) {
            _loginStatus.emit(repository.login(user))
        }
    }

    fun setCredential(credential : LoginJwtToken, dataStoreInstance: DataStoreInstance, navController: NavController){
        viewModelScope.launch(Dispatchers.Main) {
            credential.userId?.let { dataStoreInstance.setUserId(it) }
            if(credential.role == "cathering"){
                dataStoreInstance.setRole(credential.role)
            }
            dataStoreInstance.setTokenId(credential.token)
            if(credential.role == "cathering"){
                navController.navigate(NavigationEnum.ProductManagementActivity.name)
            }else{
                navController.navigate(NavigationEnum.HomeScreenActivity.name)
            }

        }
    }
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