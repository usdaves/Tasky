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

import app.tasky.auth.domain.repository.AuthRepository.Companion.MAX_DISPLAY_NAME_LENGTH
import app.tasky.auth.domain.repository.AuthRepository.Companion.MAX_PASSWORD_LENGTH
import app.tasky.auth.domain.repository.AuthRepository.Companion.MIN_DISPLAY_NAME_LENGTH
import app.tasky.auth.domain.repository.AuthRepository.Companion.MIN_PASSWORD_LENGTH
import app.tasky.auth.domain.usecase.validation.SignUpValidationResult.EmptyDisplayName
import app.tasky.auth.domain.usecase.validation.SignUpValidationResult.EmptyEmail
import app.tasky.auth.domain.usecase.validation.SignUpValidationResult.EmptyPassword
import app.tasky.auth.domain.usecase.validation.SignUpValidationResult.InvalidEmail
import app.tasky.auth.domain.usecase.validation.SignUpValidationResult.TooLongDisplayName
import app.tasky.auth.domain.usecase.validation.SignUpValidationResult.TooLongPassword
import app.tasky.auth.domain.usecase.validation.SignUpValidationResult.TooShortDisplayName
import app.tasky.auth.domain.usecase.validation.SignUpValidationResult.TooShortPassword
import app.tasky.core.domain.matcher.EmailMatcher

// Created by usdaves(Usmon Abdurakhmanov) on 3/13/2023

class SignUpValidationUseCase(
  private val emailMatcher: EmailMatcher,
) {

  operator fun invoke(
    displayName: String,
    email: String,
    password: String,
  ): SignUpValidationResult? = when {
    displayName.isEmpty() -> EmptyDisplayName
    displayName.length < MIN_DISPLAY_NAME_LENGTH -> TooShortDisplayName
    displayName.length > MAX_DISPLAY_NAME_LENGTH -> TooLongDisplayName
    email.isEmpty() -> EmptyEmail
    !emailMatcher.matches(email) -> InvalidEmail
    password.isEmpty() -> EmptyPassword
    password.length < MIN_PASSWORD_LENGTH -> TooShortPassword
    password.length > MAX_PASSWORD_LENGTH -> TooLongPassword
    else -> null
  }
}
