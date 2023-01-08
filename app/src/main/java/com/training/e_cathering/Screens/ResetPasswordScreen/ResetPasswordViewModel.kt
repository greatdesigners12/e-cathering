package com.training.e_cathering.Screens.ResetPasswordScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.e_cathering.Models.ResetPasswordRequest
import com.training.e_cathering.Models.SingleResponseData
import com.training.e_cathering.Models.TransactionGroup
import com.training.e_cathering.Models.User
import com.training.e_cathering.Repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {
    val _resetPasswordStatus = MutableSharedFlow<SingleResponseData<User>>()
    val resetPasswordStatus = _resetPasswordStatus.asSharedFlow()

    fun resetPassword(oldPassword : String, newPassword : String, user_id : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            user_id.collect{
                if(it != null){
                    authRepository.resetPassword(ResetPasswordRequest(it.toInt(), oldPassword, newPassword)).data?.let { it1 ->
                        _resetPasswordStatus.emit(
                            it1
                        )
                    }

                }
            }
        }
    }
}