package com.programmersbox.thundercounter

import platform.UIKit.UIDevice
import kotlin.math.round

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun roundToDecimals(number: Float, decimals: Int): String = number.roundDecimals(decimals).toString()

fun Float.roundDecimals(decimals: Int): Float {
    var multiplier = 1.0f
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}