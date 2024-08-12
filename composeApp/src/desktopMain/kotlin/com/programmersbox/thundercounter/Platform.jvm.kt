package com.programmersbox.thundercounter

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