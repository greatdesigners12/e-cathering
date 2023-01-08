package com.training.e_cathering.Screens.SettingScreenActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Navigation.NavigationEnum
import com.training.e_cathering.Repositories.CartRepository
import com.training.e_cathering.Repositories.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(val cartRepository: CartRepository, val transactionRepository: TransactionRepository) : ViewModel() {
    fun logout(dataStoreInstance: DataStoreInstance ,navController: NavController){
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