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

package app.tasky.auth.data.inject

import app.tasky.auth.domain.repository.AuthRepository
import app.tasky.auth.domain.usecase.auth.SignInUseCase
import app.tasky.auth.domain.usecase.auth.SignUpUseCase
import app.tasky.auth.domain.usecase.validation.SignInValidationUseCase
import app.tasky.auth.domain.usecase.validation.SignUpValidationUseCase
import app.tasky.core.domain.matcher.EmailMatcher
import app.tasky.profile.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// Created by usdaves(Usmon Abdurakhmanov) on 3/14/2023

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCaseModule {

  // The reason to not provide use cases in the domain module is just to keep it pure kotlin library

  @Provides
  fun provideSignInUseCase(
    authRepository: AuthRepository,
    profileRepository: ProfileRepository,
    signInValidationUseCase: SignInValidationUseCase,
  ): SignInUseCase = SignInUseCase(authRepository, profileRepository, signInValidationUseCase)

  @Provides
  fun provideSignUpUseCase(
    authRepository: AuthRepository,
    profileRepository: ProfileRepository,
    signUpValidationUseCase: SignUpValidationUseCase,
  ): SignUpUseCase = SignUpUseCase(authRepository, profileRepository, signUpValidationUseCase)

  @Provides
  fun provideSignInValidationUseCase(
    emailMatcher: EmailMatcher,
  ): SignInValidationUseCase = SignInValidationUseCase(emailMatcher)

  @Provides
  fun provideSignUpValidationUseCase(
    emailMatcher: EmailMatcher,
  ): SignUpValidationUseCase = SignUpValidationUseCase(emailMatcher)
}
