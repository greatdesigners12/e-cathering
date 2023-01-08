package com.training.e_cathering.Repositories

import android.content.ContentValues
import android.util.Log
import com.training.e_cathering.Models.*
import com.training.e_cathering.Utils.DataAPIWrapper
import com.training.e_cathering.network.TransactionAPI
import javax.inject.Inject

class TransactionRepository @Inject constructor(val transactionAPI : TransactionAPI)  {
    suspend fun createTransaction(transactionRequest: TransactionRequest, token : String) : DataAPIWrapper<SingleResponseData<TransactionRequest>, Boolean, Exception> {
        val data = DataAPIWrapper<SingleResponseData<TransactionRequest>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = transactionAPI.createTransaction(transactionRequest, token)
            Log.d(ContentValues.TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(ContentValues.TAG, "getAllCatherings: ${e.message}")
        }
        return data
    }

    suspend fun getTransactionGroupById(id: Int, token : String) : DataAPIWrapper<SingleResponseData<TransactionGroup>, Boolean, Exception> {
        val data = DataAPIWrapper<SingleResponseData<TransactionGroup>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = transactionAPI.getTransactionGroup(id, token)
            Log.d(ContentValues.TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(ContentValues.TAG, "getAllCatherings: ${e.message}")
        }
        return data
    }

    suspend fun getAllTransactionGroup(user_id : String, token : String) : DataAPIWrapper<Response<TransactionGroup>, Boolean, Exception> {
        val data = DataAPIWrapper<Response<TransactionGroup>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = transactionAPI.getAllTransactionGroup(user_id.toInt(), token)
            Log.d(ContentValues.TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(ContentValues.TAG, "getAllCatherings: ${e.message}")
        }
        return data
    }

    suspend fun setToSuccess(id_transaction : Int, token : String) : DataAPIWrapper<SingleResponseData<TransactionGroup>, Boolean, Exception> {
        val data = DataAPIWrapper<SingleResponseData<TransactionGroup>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = transactionAPI.setToSuccess(id_transaction, token)
            Log.d(ContentValues.TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(ContentValues.TAG, "getAllCatherings: ${e.message}")
        }
        return data
    }

    suspend fun resetIdTransaction(id_transaction : String,token : String) : DataAPIWrapper<SingleResponseData<String>, Boolean, Exception>{
        val data = DataAPIWrapper<SingleResponseData<String>, Boolean, Exception>()
        data.loading = true
        try{

            data.data = transactionAPI.resetIdTransaction(id_transaction, token)
            Log.d(ContentValues.TAG, "getAllCatherings: ${data.data}")
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(ContentValues.TAG, "getAllCatherings: ${e.message}")
        }
        return data
    }
}