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

// Created by usdaves(Usmon Abdurakhmanov) on 2/23/2023

sealed interface SignUpResult {
  object Authenticating : SignUpResult
  object SettingUpProfileInfo : SignUpResult
  object Authenticated : SignUpResult

  sealed interface Failure : SignUpResult {
    object NoNetworkConnection : Failure

    object EmailAlreadyInUse : Failure

    @JvmInline
    value class UnknownError(val message: String) : Failure
  }
}
