package com.training.e_cathering

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreInstance(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userId")
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val TOKEN_ID_KEY = stringPreferencesKey("token_id")
    }

    //get the saved email
    val getUserId: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }

    val getToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_ID_KEY] ?: ""
        }

    //save email into datastore
    suspend fun setUserId(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = name
        }
    }

    suspend fun setTokenId(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_ID_KEY] = token
        }
    }

    suspend fun deleteUserId() {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = ""
        }
    }
}