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

package app.tasky.auth.domain.usecase.auth

import app.tasky.auth.domain.repository.AuthRepository
import app.tasky.auth.domain.repository.SignUpResult
import app.tasky.auth.domain.repository.SignUpResult.Failure
import app.tasky.auth.domain.usecase.validation.SignUpValidationUseCase
import app.tasky.auth.domain.util.EmailAlreadyInUseException
import app.tasky.core.domain.util.NoNetworkConnectionException
import app.tasky.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

// Created by usdaves(Usmon Abdurakhmanov) on 3/13/2023

class SignUpUseCase(
  private val authRepository: AuthRepository,
  private val profileRepository: ProfileRepository,
  private val signInValidationUseCase: SignUpValidationUseCase,
) {

  operator fun invoke(
    displayName: String,
    email: String,
    password: String,
  ): Flow<SignUpResult> = flow {
    emit(SignUpResult.Authenticating)

    signInValidationUseCase(displayName, email, password)?.let { invalidationResult ->
      emit(Failure.InvalidCredentials(invalidationResult))
      return@flow
    }

    authRepository.signUp(email, password)
      .onSuccess { userId ->
        handleSuccessAuthResult(userId, displayName, email)
      }.onFailure { throwable ->
        handleFailureAuthResult(throwable)
      }
  }

  private suspend fun FlowCollector<SignUpResult>.handleSuccessAuthResult(
    userId: String,
    displayName: String,
    email: String,
  ) {
    emit(SignUpResult.SettingUpProfileInfo)
    profileRepository.setUserProfile(userId, displayName, email)
      .onSuccess {
        emit(SignUpResult.Completed)
      }.onFailure { throwable ->
        handleFailureProfileResult(throwable)
      }
  }

  private suspend fun FlowCollector<Failure>.handleFailureAuthResult(throwable: Throwable) {
    when (throwable) {
      is NoNetworkConnectionException -> {
        emit(Failure.NoNetworkConnection)
      }

      is EmailAlreadyInUseException -> {
        emit(Failure.EmailAlreadyInUse)
      }

      else -> {
        emit(Failure.UnknownError(throwable.localizedMessage))
      }
    }
  }

  private suspend fun FlowCollector<Failure>.handleFailureProfileResult(throwable: Throwable) {
    when (throwable) {
      is NoNetworkConnectionException -> {
        emit(Failure.NoNetworkConnection)
      }

      else -> {
        emit(Failure.UnknownError(throwable.localizedMessage))
      }
    }
  }
}
