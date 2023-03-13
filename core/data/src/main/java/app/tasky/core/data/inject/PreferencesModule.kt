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

package app.tasky.core.data.inject

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import app.tasky.core.data.preferences.DatastoreAuthPreferences
import app.tasky.core.data.preferences.DatastoreOnboardingPreferences
import app.tasky.core.data.preferences.DatastoreProfilePreferences
import app.tasky.core.domain.preferences.AuthPreferences
import app.tasky.core.domain.preferences.OnboardingPreferences
import app.tasky.core.domain.preferences.ProfilePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

// Created by usdaves(Usmon Abdurakhmanov) on 3/13/2023

private const val DATASTORE_PREFERENCES_NAME = "io.usdaves.tasky.preferences"

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

  private val Context.dataStore by preferencesDataStore(DATASTORE_PREFERENCES_NAME)

  @Provides
  fun provideAuthPreferences(
    @ApplicationContext context: Context,
  ): AuthPreferences = DatastoreAuthPreferences(context.dataStore)

  @Provides
  fun provideOnboardingPreferences(
    @ApplicationContext context: Context,
  ): OnboardingPreferences = DatastoreOnboardingPreferences(context.dataStore)

  @Provides
  fun provideProfilePreferences(
    @ApplicationContext context: Context,
  ): ProfilePreferences = DatastoreProfilePreferences(context.dataStore)
}
