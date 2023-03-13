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
import app.tasky.core.domain.preferences.OnboardingPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Created by usdaves(Usmon Abdurakhmanov) on 3/13/2023

internal class DatastoreOnboardingPreferences(
  private val dataStore: DataStore<Preferences>,
) : OnboardingPreferences {

  override val isOnboardingCompleted: Flow<Boolean> = dataStore.data.map { preference ->
    preference[ONBOARDING_KEY] ?: false
  }

  override suspend fun setOnboardingCompleted(isOnboardingCompleted: Boolean) {
    dataStore.edit { preferences ->
      preferences[ONBOARDING_KEY] = isOnboardingCompleted
    }
  }

  private companion object {
    val ONBOARDING_KEY = booleanPreferencesKey("onboarding_pref_key")
  }
}
