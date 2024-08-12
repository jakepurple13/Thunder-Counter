package com.programmersbox.thundercounter

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun roundToDecimals(number: Float, decimals: Int): String = number.toString().take(2 + decimals)

@Composable
actual fun ColorScheme(isDarkMode: Boolean): ColorScheme = when {
    isDarkMode -> darkColorScheme()
    else -> lightColorScheme()
}