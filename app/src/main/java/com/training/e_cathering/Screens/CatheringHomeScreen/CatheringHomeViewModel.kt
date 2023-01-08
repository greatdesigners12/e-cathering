package com.training.e_cathering.Screens.CatheringHomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Navigation.NavigationEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CatheringHomeViewModel @Inject constructor() : ViewModel() {
    fun logout(dataStoreInstance: DataStoreInstance, navController: NavController){
        viewModelScope.launch(Dispatchers.Main) {
            dataStoreInstance.deleteToken()
            dataStoreInstance.deleteUserId()
            dataStoreInstance.deleteRole()
            navController.navigate(NavigationEnum.LoginScreenActivity.name){
                popUpTo(NavigationEnum.LoginScreenActivity.name)
            }
        }
    }
}