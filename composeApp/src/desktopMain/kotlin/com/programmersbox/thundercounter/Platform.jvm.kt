package com.programmersbox.thundercounter

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import kotlin.math.round

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun roundToDecimals(number: Float, decimals: Int): String = number.roundDecimals(decimals).toString()

fun Float.roundDecimals(decimals: Int): Float {
    var multiplier = 1.0f
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

@Composable
actual fun ColorScheme(isDarkMode: Boolean): ColorScheme = when {
    isDarkMode -> darkColorScheme()
    else -> lightColorScheme()
}