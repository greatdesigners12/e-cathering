package com.training.e_cathering.Screens.TransactionDetailScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.*
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.training.e_cathering.Components.CustomAlertDialog
import com.training.e_cathering.Components.textInputField
import com.training.e_cathering.Constant
import com.training.e_cathering.DataStoreInstance
import com.training.e_cathering.Models.Review
import com.training.e_cathering.Models.TransactionGroup


@Composable
fun TransactionDetailActivity(navController: NavController, id : String ,viewModel: TransactionDetailViewModel) {

    val context = LocalContext.current
    viewModel.context = context
    val dataStore = DataStoreInstance(context)

    val curData = remember{
        mutableStateOf<TransactionGroup?>(null)
    }

    val hidePayButton = remember{
        mutableStateOf(false)
    }

    val snapToken = remember{
        mutableStateOf("")
    }

    val showReviewModal = remember{
        mutableStateOf(false)
    }

    val reviewMsg = remember{
        mutableStateOf("")
    }

    val rating = remember{
        mutableStateOf("")
    }

    val userReviews = remember{
        mutableStateListOf<Review>()
    }

    LaunchedEffect(key1 = true){
        viewModel.getTransactionGroupById(id.toInt(), dataStore.getToken)
        viewModel.getReview(dataStore.getUserId, id, dataStore.getToken)
    }

    LaunchedEffect(key1 = viewModel.transactionGroup.collectAsState(initial = "").value){
        viewModel.transactionGroup.collect{
            curData.value = it
            snapToken.value = it.snap_token
        }
    }

    LaunchedEffect(key1 = viewModel.newIdTransaction.collectAsState(initial = "").value){
        viewModel.newIdTransaction.collect{
            viewModel.getTransactionGroupById(id.toInt(), dataStore.getToken)
        }
    }

    LaunchedEffect(key1 = viewModel.addReviewStatus.collectAsState(initial = "").value){
        viewModel.addReviewStatus.collect{
            viewModel.getTransactionGroupById(id.toInt(), dataStore.getToken)
        }
    }

    LaunchedEffect(key1 = viewModel.userReview.collectAsState(initial = "").value){
        viewModel.userReview.collect{
            userReviews.clear()
            userReviews.addAll(it)
        }
    }

    if(showReviewModal.value){
        CustomAlertDialog(title="Add Review", height = 350.dp, onDismiss = {
            showReviewModal.value = false
        }){
            Column() {
                textInputField(label = "Review message", inputValue = reviewMsg.value){
                    reviewMsg.value = it
                }
                textInputField(label = "Rating", inputValue = rating.value, keyboardType = KeyboardType.Decimal ){
                    rating.value = it
                }
                Button(onClick = {
                    viewModel.createReview(Review(null, null, id.toInt(), reviewMsg.value, rating.value.toFloat()), dataStore.getUserId, dataStore.getToken)
                    showReviewModal.value = false
                }) {
                    Text("Create Review")
                }
            }


        }
    }

    if(curData.value != null){
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)){
            item {
                Text("Alamat", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Row(){
                    Text(curData.value!!.full_address)
                    
                }



            }

            items(items= curData.value!!.transactionGroupRelation){ item ->
                Spacer(modifier = Modifier.height(10.dp))
                Card(modifier = Modifier.height(100.dp)) {
                    Row(modifier = Modifier.fillMaxSize()){

                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            Modifier
                                .fillMaxWidth(0.7f)
                                .fillMaxHeight()) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Text(text = item.transactionProduct.name)
                                Text(text = "Rp. " + item.transactionProduct.price.toString())
                                Row(modifier = Modifier.height(30.dp)){
                                    Box(modifier = Modifier
                                        .fillMaxHeight()
                                        .clip(shape = RoundedCornerShape(size = 12.dp))
                                        .background(MaterialTheme.colors.primary)
                                        .padding(5.dp)
                                    ) {
                                        Text("One time", color = Color.White)
                                    }


                                }

                            }


                        }
                    }
                }
            }
            item{
                Column(){
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("Shipping price", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(curData.value!!.shipping_price.toString(), fontSize = 17.sp)
                    Text("Total price", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text("Rp. " + curData.value!!.totalPrice.toString(), fontSize = 17.sp)
                }

            }
            item{
                if(curData.value!!.status == "Belum terbayar" && !hidePayButton.value){
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 10.dp), contentAlignment = Alignment.BottomCenter){
                        ExtendedFloatingActionButton(
                            // on below line we are setting text for our fab
                            text = { Text(text = "Pay Now") },
                            // on below line we are adding click listener.
                            onClick = {
                                val sdk = SdkUIFlowBuilder.init()
                                    .setClientKey(Constant.client_key) // client_key is mandatory
                                    .setContext(context) // context is mandatory
                                    .setTransactionFinishedCallback {result ->
                                        if (result.getResponse() != null) {
                                            when (result.getStatus()) {
                                                TransactionResult.STATUS_SUCCESS ->{
                                                    viewModel.setToSuccess(result.response.transactionId, dataStore.getToken)
                                                    hidePayButton.value = true
                                                    viewModel.getTransactionGroupById(id.toInt(), dataStore.getToken)
                                                }
                                                TransactionResult.STATUS_PENDING -> {
                                                     viewModel.resetIdTransaction(curData!!.value!!.idTransaction, dataStore.getToken)
                                                }
                                                TransactionResult.STATUS_FAILED -> Toast.makeText(
                                                    context,
                                                    "Transaction Failed. ID: " + result.getResponse()
                                                        .getTransactionId()
                                                        .toString() + ". Message: " + result.getResponse()
                                                        .getStatusMessage(),
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                            result.getResponse().getValidationMessages()
                                        } else if (result.isTransactionCanceled()) {
                                            viewModel.resetIdTransaction(curData!!.value!!.idTransaction, dataStore.getToken)
                                        } else {
                                            if (result.getStatus()
                                                    .equals(TransactionResult.STATUS_INVALID)
                                            ) {
                                                Toast.makeText(
                                                    context,
                                                    "Transaction Invalid",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Transaction Finished with failure.",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }
                                    } // set transaction finish callback (sdk callback)
                                    .setMerchantBaseUrl(Constant.midtrans_base_url) //set merchant url (required)
                                    .enableLog(true) // enable sdk log (optional)
                                    .setColorTheme(
                                        CustomColorTheme(
                                            "#FFE51255",
                                            "#B61548",
                                            "#FFE51255"
                                        )
                                    ) // set theme. it will replace theme on snap theme on MAP ( optional)
                                    .buildSDK()
                                val transactionRequest =
                                    com.midtrans.sdk.corekit.core.TransactionRequest(
                                        curData!!.value!!.idTransaction,
                                        curData!!.value!!.totalPrice.toDouble()
                                    )
                                val customerDetails = CustomerDetails()
                                customerDetails.setCustomerIdentifier(curData!!.value?.User?.email)

                                customerDetails.setFirstName(curData!!.value?.User?.email)
                                customerDetails.setLastName(curData!!.value?.User?.email)
                                customerDetails.setEmail(curData!!.value?.User?.email)

                                val shippingAddress = ShippingAddress()
                                shippingAddress.address = curData.value!!.full_address

                                customerDetails.setShippingAddress(shippingAddress)

                                val billingAddress = BillingAddress()
                                billingAddress.address = curData.value!!.full_address

                                customerDetails.setBillingAddress(billingAddress)

                                val itemDetailsList: ArrayList<ItemDetails> = ArrayList()

                                for(item in curData.value!!.transactionGroupRelation){
                                    itemDetailsList.add(ItemDetails(item.transactionProductId.toString(),
                                        item.transactionProduct.price.toDouble() * item.transactionProduct.time, item.transactionProduct.portion, item.transactionProduct.name))
                                }


                                transactionRequest.itemDetails = itemDetailsList
                                transactionRequest.setCustomerDetails(customerDetails)
                                val billInfoModel = BillInfoModel("BILL_INFO_KEY", "BILL_INFO_VALUE")
                                
                                transactionRequest.billInfoModel = billInfoModel
                                sdk.setTransactionRequest(transactionRequest)
                                if(snapToken.value != ""){
                                    sdk.startPaymentUiFlow(context, snapToken.value)

                                }else{
                                    sdk.startPaymentUiFlow(context)

                                }


                            },
                            // on below line adding
                            // a background color.
                            backgroundColor = MaterialTheme.colors.primary,
                            // on below line we are
                            // adding a content color.
                            contentColor = Color.White,
                            // on below line we are
                            // adding icon for our fab
                            icon = { Icon(Icons.Filled.Add, "") }
                        )
                    }
                }else if(curData.value!!.status == "Terbayar"){
                    if(userReviews.size == 0){
                        Button(onClick = {
                            showReviewModal.value = true
                        }){
                            Text("Make Review")
                        }
                    }

                }else{
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 10.dp), contentAlignment = Alignment.BottomCenter){
                        ExtendedFloatingActionButton(
                            // on below line we are setting text for our fab
                            text = { Text(text = "Pesanan akan diantar ke tempatmu") },
                            // on below line we are adding click listener.
                            onClick = {
                                Toast.makeText(context, "Harap Menunggu", Toast.LENGTH_LONG)
                            },
                            // on below line adding
                            // a background color.
                            backgroundColor = MaterialTheme.colors.primary,
                            // on below line we are
                            // adding a content color.
                            contentColor = Color.White,
                            // on below line we are
                            // adding icon for our fab
                            icon = { Icon(Icons.Filled.Add, "") }
                        )
                    }
                }

            }
        }
    }else{
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }

}

