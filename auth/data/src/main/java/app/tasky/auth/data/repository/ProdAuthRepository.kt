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

package app.tasky.auth.data.repository

import app.tasky.auth.data.remote.AuthApi
import app.tasky.auth.domain.repository.AuthRepository
import app.tasky.auth.domain.util.EmailAlreadyInUseException
import app.tasky.auth.domain.util.InvalidCredentialsException
import app.tasky.core.domain.preferences.AuthPreferences
import app.tasky.core.domain.util.NoNetworkConnectionException
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException

// Created by usdaves(Usmon Abdurakhmanov) on 3/7/2023

internal class ProdAuthRepository(
  private val authApi: AuthApi,
  private val authPreferences: AuthPreferences,
) : AuthRepository {

  override suspend fun signIn(email: String, password: String): Result<String> = safeApiCall {
    val userId = authApi.signIn(email, password)
    authPreferences.setAuthenticated(isAuthenticated = true)
    return@safeApiCall userId
  }

  override suspend fun signUp(email: String, password: String): Result<String> = safeApiCall {
    val userId = authApi.signUp(email, password)
    authPreferences.setAuthenticated(isAuthenticated = true)
    return@safeApiCall userId
  }

  private inline fun safeApiCall(block: () -> String) = try {
    val userId = block()
    Result.success(userId)
  } catch (exception: FirebaseNetworkException) {
    Result.failure(NoNetworkConnectionException(exception))
  } catch (exception: FirebaseAuthException) {
    Result.failure(errorCodeAndExceptionMap[exception.errorCode] ?: exception)
  } catch (exception: FirebaseException) {
    Result.failure(exception)
  }

  private val errorCodeAndExceptionMap = mapOf(
    "ERROR_USER_NOT_FOUND" to InvalidCredentialsException(),
    "ERROR_EMAIL_ALREADY_IN_USE" to EmailAlreadyInUseException(),
  )
}
