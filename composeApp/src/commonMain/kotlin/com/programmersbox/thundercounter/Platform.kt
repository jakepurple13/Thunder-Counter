package com.programmersbox.thundercounter

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun roundToDecimals(number: Float, decimals: Int): String

@Composable
expect fun ColorScheme(isDarkMode: Boolean): ColorScheme