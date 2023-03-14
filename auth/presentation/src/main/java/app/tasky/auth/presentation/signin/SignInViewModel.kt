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

package app.tasky.auth.presentation.signin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.tasky.auth.domain.repository.SignInResult
import app.tasky.auth.domain.usecase.auth.SignInUseCase
import app.tasky.auth.domain.usecase.validation.SignInValidationResult
import app.tasky.auth.presentation.R
import app.tasky.auth.presentation.signin.SignInViewEvent.NavigateToMain
import app.tasky.auth.presentation.signin.SignInViewEvent.NavigateViewSignUp
import app.tasky.auth.presentation.signin.SignInViewEvent.ShowMessage
import app.tasky.core.presentation.Text
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

// Created by usdaves(Usmon Abdurakhmanov) on 3/14/2023

private const val VIEW_STATE = "sign_in_screen_state_handle_key"

@HiltViewModel
internal class SignInViewModel @Inject constructor(
  private val signInUseCase: SignInUseCase,
  private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

  val viewState = savedStateHandle.getStateFlow(VIEW_STATE, SignInViewState())

  private val viewEventChannel = Channel<SignInViewEvent>()
  val viewEvent = viewEventChannel.receiveAsFlow()

  fun onEmailChanged(email: String) {
    savedStateHandle[VIEW_STATE] = viewState.value.copy(
      email = email,
      emailError = null,
    )
  }

  fun onPasswordChanged(password: String) {
    savedStateHandle[VIEW_STATE] = viewState.value.copy(
      password = password,
      passwordError = null,
    )
  }

  fun onContinueWithGoogleClicked() {
    viewEventChannel.trySend(ShowMessage(Text(R.string.signing_with_google_not_supported)))
  }

  fun onSignUpClicked() {
    viewEventChannel.trySend(NavigateViewSignUp)
  }

  fun onSignInClicked() {
    viewModelScope.launch(Dispatchers.IO) {
      val signInResultFlow = signInUseCase(
        email = viewState.value.email,
        password = viewState.value.password,
      )
      signInResultFlow.collect(::signInResultHandler)
    }
  }

  private fun signInResultHandler(signInResult: SignInResult) {
    when (signInResult) {
      SignInResult.Authenticating -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          loadingText = Text(R.string.authenticating),
        )
      }

      SignInResult.RetrievingProfileInfo -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          loadingText = Text(R.string.retrieving_profile_info),
        )
      }

      SignInResult.Completed -> {
        viewEventChannel.trySend(NavigateToMain)
        savedStateHandle[VIEW_STATE] = SignInViewState()
      }

      is SignInResult.Failure -> handleFailureSignInResult(signInResult)
    }
  }

  private fun handleFailureSignInResult(failure: SignInResult.Failure) {
    savedStateHandle[VIEW_STATE] = viewState.value.copy(loadingText = null)
    when (failure) {
      is SignInResult.Failure.InvalidCredentials -> handleInvalidCredentialsResult(failure.cause)

      SignInResult.Failure.NoNetworkConnection -> {
        viewEventChannel.trySend(ShowMessage(R.string.no_network_connection))
      }

      is SignInResult.Failure.UnknownError -> {
        viewEventChannel.trySend(ShowMessage(failure.message))
      }
    }
  }

  private fun handleInvalidCredentialsResult(invalidationResult: SignInValidationResult) {
    when (invalidationResult) {
      SignInValidationResult.EmptyEmail -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          emailError = Text(R.string.empty_email),
        )
      }

      SignInValidationResult.EmptyPassword -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          passwordError = Text(R.string.empty_password),
        )
      }

      SignInValidationResult.InvalidCredentials -> {
        viewEventChannel.trySend(ShowMessage(R.string.invalid_credentials))
      }
    }
  }
}
