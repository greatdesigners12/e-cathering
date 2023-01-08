package com.training.e_cathering.Screens.CreateCatheringScreenActivity

import android.app.TimePickerDialog
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.training.e_cathering.Components.*
import com.training.e_cathering.Models.CreateCathering
import com.training.e_cathering.Models.User
import com.training.e_cathering.Screens.RegisterScreen.RegisterViewModel
import java.util.*


@Composable
fun CreateCatheringScreenActivity(viewModel: CreateCatheringScreenActivityViewModel) {
    val emailInput = remember{
        mutableStateOf("")
    }

    val passwordInput = remember{
        mutableStateOf("")
    }
    val inputCatheringName = remember{
        mutableStateOf("")
    }
    val inputDescription = remember{
        mutableStateOf("")
    }


    val logoImage = remember{mutableStateOf<Uri?>(null)}

    val menuImage = remember{mutableStateOf<Uri?>(null)}



    val showAlertDialog = remember{
        mutableStateOf(false)
    }

    val alertDialogMsg = remember{
        mutableStateOf("")
    }

    val loadingProgress = remember {
        mutableStateOf(false)
    }

    val passwordVisible = remember{
        mutableStateOf(false)
    }
    val mContext = LocalContext.current


    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]


    val openTime = remember { mutableStateOf("") }
    val closeTime = remember { mutableStateOf("") }

    val open = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            openTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )
    val close = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            closeTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )




    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        val mContext = LocalContext.current
        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp))
        {
            Text(text = "Daftar Cathering", textAlign = TextAlign.Center, style= MaterialTheme.typography.h4)

        }
        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)) {
            Text(text = "Logo Cathering", style = MaterialTheme.typography.h5, modifier = Modifier.align(Alignment.Center))
        }
        ImagePickerView(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            lastSelectedImage = logoImage.value,
            onSelection = {
                logoImage.value = it
            }
        )
        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)) {
            Text(text = "Email", style = MaterialTheme.typography.h5)
        }
        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)){
            basicInputField(label = "Email", inputValue = emailInput.value){
                emailInput.value = it
            }
        }
        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)) {
            Text(text = "Password", style = MaterialTheme.typography.h5)
        }
        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)){
            passwordInputField(passwordVisible = passwordVisible.value, inputValue = passwordInput.value, pwdIcon = {passwordVisible.value = !passwordVisible.value}){
                passwordInput.value = it
            }
        }
        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)) {
            Text(text = "Nama Cathering", style = MaterialTheme.typography.h5)
        }
        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)){
            basicInputField(label = "Nama Cathering", inputValue = inputCatheringName.value){
                inputCatheringName.value=it
            }
        }
        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)) {
            Text(text = "Menu Yang Akan Ada Di Cathering", style = MaterialTheme.typography.h5,modifier = Modifier.align(Alignment.Center))
        }
        ImagePickerView(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            lastSelectedImage = menuImage.value,
            onSelection = {
                menuImage.value = it
            }
        )

        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)) {
            Text(text = "Deskripsi Cathering", style = MaterialTheme.typography.h5)
        }
        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)){
            basicInputField(label = "Deskripsi Cathering", inputValue = inputDescription.value){
                inputDescription.value=it
            }
        }
        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column() {
                    Button(
                        onClick = { open.show() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))
                    ) {
                        Text(text = "Jam Buka", color = Color.White)


                         }
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "Jam Buka: ", fontSize = 20.sp)
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "${openTime.value}", fontSize = 20.sp)

                }
                Column() {
                    Text(text = "~", fontSize = 24.sp,modifier = Modifier.padding(top = 7.dp))
                }

                Column() {
                    Button(
                        onClick = { close.show() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))
                    ) {
                        Text(text = "Jam Tutup", color = Color.White)
                                }
                    Spacer(modifier = Modifier.size(10.dp))

                    Text(text = "Jam Tutup:", fontSize = 20.sp,modifier = Modifier.align(Alignment.End))
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "${closeTime.value}", fontSize = 20.sp, modifier = Modifier.align(Alignment.End
                    ))

                }
            }





        }




        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 48.dp)){
            Button(onClick = {

                logoImage.value?.let {it->
                    menuImage.value?.let{it1->
                    viewModel.register(
                        CreateCathering(closeTime.value, inputDescription.value, emailInput.value, "","", "",
                            inputCatheringName.value,openTime.value, passwordInput.value,"","",0)
                        ,it, it1
                    )
                }}
                Toast.makeText(mContext, "Cathering Berhasil Di Buat", Toast.LENGTH_SHORT).show()
            }) {

                Text(text = "Daftar", fontSize = 20.sp)
            }}
}
}
