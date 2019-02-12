package com.androidacademy.msk.exerciseproject.data.network

enum class SocialNetworkApp(val appPackage: String, val accountUrl: String) {

    INSTAGRAM("com.instagram.android", "https://www.instagram.com/georgy.ryabykh/"),
    TELEGRAM("org.telegram.messenger", "https://t.me/georgy_ryabykh/"),
    TELEGRAM_X("org.thunderdog.challegram", TELEGRAM.accountUrl)
}
