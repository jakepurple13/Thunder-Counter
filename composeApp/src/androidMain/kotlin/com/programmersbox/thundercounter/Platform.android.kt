package com.programmersbox.thundercounter

import android.os.Build
import kotlin.math.round

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun roundToDecimals(number: Float, decimals: Int): String = number.roundDecimals(decimals).toString()

fun Float.roundDecimals(decimals: Int): Float {
    var multiplier = 1.0f
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}