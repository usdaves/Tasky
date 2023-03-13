/*
 * Copyright 2023 usdaves(Usmon Abdurakhmanov)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.tasky.core.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import app.tasky.core.domain.preferences.AuthPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Created by usdaves(Usmon Abdurakhmanov) on 3/13/2023

internal class DatastoreAuthPreferences(
  private val dataStore: DataStore<Preferences>,
) : AuthPreferences {

  override val isAuthenticated: Flow<Boolean> = dataStore.data.map { preference ->
    preference[AUTH_KEY] ?: false
  }

  override suspend fun setAuthenticated(isAuthenticated: Boolean) {
    dataStore.edit { preferences ->
      preferences[AUTH_KEY] = isAuthenticated
    }
  }

  private companion object {
    val AUTH_KEY = booleanPreferencesKey("is_authenticated_pref_key")
  }
}
