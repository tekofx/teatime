package dev.tekofx.teatime

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform