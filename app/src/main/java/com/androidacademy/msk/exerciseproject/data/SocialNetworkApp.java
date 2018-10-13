package com.androidacademy.msk.exerciseproject.data;

import androidx.annotation.NonNull;

public enum SocialNetworkApp {

    INSTAGRAM("com.instagram.android", "https://www.instagram.com/georgy.ryabykh/"),
    TELEGRAM("org.telegram.messenger", "https://t.me/georgy_ryabykh/"),
    TELEGRAM_X("org.thunderdog.challegram", TELEGRAM.getAccountUrl());

    @NonNull
    private final String appPackage;
    @NonNull
    private final String accountUrl;

    SocialNetworkApp(@NonNull String appPackage, @NonNull String accountUrl) {
        this.appPackage = appPackage;
        this.accountUrl = accountUrl;
    }

    @NonNull
    public String getAppPackage() {
        return appPackage;
    }

    @NonNull
    public String getAccountUrl() {
        return accountUrl;
    }
}
