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

package app.tasky.core.presentation

import android.content.Context
import androidx.annotation.StringRes
import java.io.Serializable

// Created by usdaves(Usmon Abdurakhmanov) on 3/6/2023

data class Text internal constructor(
  private val value: Any?,
) : Serializable {

  fun getString(context: Context): String = when (value) {
    is Resource -> context.getString(value.resId, value.arguments)
    else -> value as String
  }

  fun getOrNull(): String? = when (value) {
    is Resource -> null
    else -> value as String
  }

  override fun toString(): String = when (value) {
    is Resource -> value.toString() // Resource(resId=$resId, arguments=${arguments.contentToString()})
    else -> "Text(value=$value)"
  }

  companion object {
    operator fun invoke(resId: Int, vararg args: Any): Text = Text(Resource(resId, args))
    operator fun invoke(value: String): Text = Text(value)
  }

  internal class Resource(
    @StringRes val resId: Int,
    val arguments: Array<out Any>,
  ) : Serializable {

    override fun equals(other: Any?): Boolean =
      other is Resource && resId == other.resId && arguments.contentEquals(other.arguments)

    override fun hashCode(): Int = 31 * resId + arguments.contentHashCode()

    override fun toString(): String = "Resource(resId=$resId, arguments=${arguments.contentToString()})"
  }
}
