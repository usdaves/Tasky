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

package app.tasky.profile.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Created by usdaves(Usmon Abdurakhmanov) on 3/14/2023

internal class FirebaseProfileApi(
  firebaseFirestore: FirebaseFirestore,
) : ProfileApi {

  private val profilesCollection = firebaseFirestore.collection("user_profiles")

  override suspend fun fetchUserProfileById(userId: String): UserProfile {
    val profileDocument = profilesCollection.document(userId).get().await()
    val profileData = profileDocument.data ?: emptyMap()
    return UserProfile(
      displayName = profileData["display_name"] as String?,
      email = profileData["email"] as String?,
    )
  }

  override suspend fun setUserProfile(userId: String, displayName: String, email: String) {
    val profileDocument = profilesCollection.document(userId)
    val profileData = mapOf(
      "display_name" to displayName,
      "email" to email,
    )
    profileDocument.set(profileData).await()
  }
}
