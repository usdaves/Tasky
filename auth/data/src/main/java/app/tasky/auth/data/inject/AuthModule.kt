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

import app.tasky.auth.data.remote.AuthApi
import app.tasky.auth.data.remote.FirebaseAuthApi
import app.tasky.auth.data.repository.ProdAuthRepository
import app.tasky.auth.domain.repository.AuthRepository
import app.tasky.core.domain.preferences.AuthPreferences
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// Created by usdaves(Usmon Abdurakhmanov) on 3/14/2023

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

  @Provides
  fun provideAuthApi(
    firebaseAuth: FirebaseAuth,
  ): AuthApi = FirebaseAuthApi(firebaseAuth)

  @Provides
  fun provideAuthRepository(
    authApi: AuthApi,
    authPreferences: AuthPreferences,
  ): AuthRepository = ProdAuthRepository(authApi, authPreferences)
}
