/*
 * Copyright (C) 2021 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.cash.quickjs

val ktBridge = KtBridge(object : InternalBridge {
  // Lazily fetch the bridge to call them.
  private val outboundBridge: dynamic
    get() = js("""globalThis.app_cash_quickjs_ktbridge_outboundBridge""")

  override fun invokeJs(
    instanceName: String,
    funName: String,
    encodedArguments: ByteArray
  ): ByteArray {
    return outboundBridge.invokeJs(instanceName, funName, encodedArguments)
  }
}).apply {
  // Eagerly publish the bridge so they can call us.
  val inboundBridge = inboundBridge
  js("""globalThis.app_cash_quickjs_ktbridge_inboundBridge = inboundBridge""")
}