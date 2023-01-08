package com.training.e_cathering

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
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
        val USER_ROLE = stringPreferencesKey("role")
    }

    //get the saved email
    val getUserId: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }
    val getRole: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ROLE] ?: ""
        }

    val getToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_ID_KEY] ?: ""
        }

    //save email into datastore
    suspend fun setUserId(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = id.toString()

        }
    }

    suspend fun setRole(role: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ROLE] = role

        }
    }

    suspend fun setTokenId(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_ID_KEY] = token
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_ID_KEY] = ""
        }
    }

    suspend fun deleteRole() {
        context.dataStore.edit { preferences ->
            preferences[USER_ROLE] = ""
        }
    }

    suspend fun deleteUserId() {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = ""
        }
    }
}