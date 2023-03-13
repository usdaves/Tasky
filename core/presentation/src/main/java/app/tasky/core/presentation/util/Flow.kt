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

package app.tasky.core.presentation.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// Created by usdaves(Usmon Abdurakhmanov) on 3/13/2023

fun <T> Fragment.collectLatest(flow: Flow<T>, collector: suspend (T) -> Unit) {
  viewLifecycleOwner.lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
      flow.collectLatest(collector)
    }
  }
}

fun <T> AppCompatActivity.collectLatest(flow: Flow<T>, collector: suspend (T) -> Unit) {
  lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
      flow.collectLatest(collector)
    }
  }
}
