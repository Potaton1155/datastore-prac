package com.tomo_app.myapplication

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "settings")

class MyRepository @Inject constructor(
    private val context: Context,
) {
    private val DATA_KEY = stringPreferencesKey("data")
    private val COUNT_KEY = intPreferencesKey("count")

    val dataFlow: Flow<Profile> = context.dataStore.data
        .catch {
            if (it is Exception) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            val data = it[DATA_KEY] ?: "nullです"
            val count = it[COUNT_KEY] ?: 0
            Profile(data, count)
        }

    suspend fun saveData(profile: Profile) {
        context.dataStore.edit {
            it[DATA_KEY] = profile.data
            it[COUNT_KEY] = profile.count
        }
    }
}