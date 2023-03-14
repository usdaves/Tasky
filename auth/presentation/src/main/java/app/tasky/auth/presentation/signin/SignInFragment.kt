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

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import app.tasky.auth.presentation.R
import app.tasky.auth.presentation.databinding.FragmentSignInBinding
import app.tasky.core.presentation.util.collectLatest
import com.skydoves.bindables.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

// Created by usdaves(Usmon Abdurakhmanov) on 3/14/2023

@AndroidEntryPoint
class SignInFragment : BindingFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

  private val viewModel: SignInViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel

    collectLatest(viewModel.viewEvent) { event ->
      when (event) {
        SignInViewEvent.NavigateToMain -> TODO("Not yet implemented")

        SignInViewEvent.NavigateViewSignUp -> TODO("Not yet implemented")

        is SignInViewEvent.ShowMessage -> {
          Toast.makeText(requireContext(), event.message.getString(requireContext()), Toast.LENGTH_SHORT).show()
        }
      }
    }
  }
}
