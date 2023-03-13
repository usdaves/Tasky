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
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import app.tasky.core.domain.preferences.ProfilePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Created by usdaves(Usmon Abdurakhmanov) on 3/13/2023

internal class DatastoreProfilePreferences(
  private val dataStore: DataStore<Preferences>,
) : ProfilePreferences {

  override val profile: Flow<ProfilePreferences.Profile> = dataStore.data.map { preferences ->
    ProfilePreferences.Profile(
      displayName = preferences[DISPLAY_NAME_KEY],
      email = preferences[EMAIL_KEY],
    )
  }

  override suspend fun setProfile(profile: ProfilePreferences.Profile) {
    dataStore.edit { preferences ->
      preferences[DISPLAY_NAME_KEY] = profile.displayName ?: "Unknown"
      preferences[EMAIL_KEY] = profile.email ?: "Unknown"
    }
  }

  private companion object {
    val DISPLAY_NAME_KEY = stringPreferencesKey("display_name_pref_key")
    val EMAIL_KEY = stringPreferencesKey("email_pref_key")
  }
}
