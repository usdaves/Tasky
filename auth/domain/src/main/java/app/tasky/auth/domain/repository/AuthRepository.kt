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

package app.tasky.auth.domain.repository

import kotlinx.coroutines.flow.Flow

// Created by usdaves(Usmon Abdurakhmanov) on 3/13/2023

interface AuthRepository {
  fun signIn(email: String, password: String): Flow<SignInResult>
  fun signUp(displayName: String, email: String, password: String): Flow<SignUpResult>

  companion object {
    const val MIN_DISPLAY_NAME_LENGTH = 2
    const val MAX_DISPLAY_NAME_LENGTH = 32
    const val MIN_PASSWORD_LENGTH = 8
    const val MAX_PASSWORD_LENGTH = 64
  }
}
