package com.example.db

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform