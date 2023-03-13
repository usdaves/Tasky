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

package app.tasky.auth.domain.usecase.validation

import app.tasky.auth.domain.repository.AuthRepository.Companion.MAX_PASSWORD_LENGTH
import app.tasky.auth.domain.repository.AuthRepository.Companion.MIN_PASSWORD_LENGTH
import app.tasky.auth.domain.usecase.validation.SignInValidationResult.EmptyEmail
import app.tasky.auth.domain.usecase.validation.SignInValidationResult.EmptyPassword
import app.tasky.auth.domain.usecase.validation.SignInValidationResult.InvalidCredentials
import app.tasky.core.domain.matcher.EmailMatcher

// Created by usdaves(Usmon Abdurakhmanov) on 3/13/2023

class SignInValidationUseCase(
  private val emailMatcher: EmailMatcher,
) {

  operator fun invoke(
    email: String,
    password: String,
  ): SignInValidationResult? = when {
    email.isEmpty() -> EmptyEmail
    password.isEmpty() -> EmptyPassword
    !emailMatcher.matches(email) || password.length !in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH -> InvalidCredentials
    else -> null
  }
}
