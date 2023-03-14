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

import androidx.annotation.StringRes
import app.tasky.core.presentation.Text

// Created by usdaves(Usmon Abdurakhmanov) on 3/14/2023

sealed interface SignInViewEvent {

  object NavigateViewSignUp : SignInViewEvent

  object NavigateToMain : SignInViewEvent

  @JvmInline
  value class ShowMessage(val message: Text) : SignInViewEvent {
    constructor(@StringRes resId: Int) : this(Text(resId))
    constructor(message: String) : this(Text(message))
  }
}
