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

package app.tasky.profile.data.repository

import app.tasky.core.domain.preferences.ProfilePreferences
import app.tasky.core.domain.util.resultOf
import app.tasky.profile.data.remote.ProfileApi
import app.tasky.profile.domain.repository.ProfileRepository

// Created by usdaves(Usmon Abdurakhmanov) on 3/14/2023

internal class FirebaseProfileRepository(
  private val profileApi: ProfileApi,
  private val profilePreferences: ProfilePreferences,
) : ProfileRepository {

  override suspend fun fetchUserProfileById(userId: String): Result<Unit> = resultOf {
    val profileApiResult = profileApi.fetchUserProfileById(userId)
    val profile = ProfilePreferences.Profile(
      displayName = profileApiResult.displayName,
      email = profileApiResult.email,
    )
    profilePreferences.setProfile(profile)
  }

  override suspend fun setUserProfile(
    userId: String,
    displayName: String,
    email: String,
  ): Result<Unit> = resultOf {
    profileApi.setUserProfile(userId, displayName, email)
    val profile = ProfilePreferences.Profile(
      displayName = displayName,
      email = email,
    )
    profilePreferences.setProfile(profile)
  }
}
