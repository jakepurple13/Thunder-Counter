package com.programmersbox.thundercounter

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun roundToDecimals(number: Float, decimals: Int): String = number.toString().take(2 + decimals)
