package com.programmersbox.thundercounter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun roundToDecimals(number: Float, decimals: Int): String