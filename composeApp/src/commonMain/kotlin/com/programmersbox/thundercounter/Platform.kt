package com.programmersbox.thundercounter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform