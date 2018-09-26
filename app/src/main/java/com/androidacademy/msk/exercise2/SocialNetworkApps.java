package com.androidacademy.msk.exercise2;

public enum SocialNetworkApps {

    INSTAGRAM("com.instagram.android", "https://www.instagram.com/georgy.ryabykh/"),
    TELEGRAM("org.telegram.messenger", "https://t.me/georgy_ryabykh/"),
    TELEGRAM_X("org.thunderdog.challegram", TELEGRAM.getAccountUrl());

    private final String appPackage;
    private final String accountUrl;

    SocialNetworkApps(String appPackage, String accountUrl) {
        this.appPackage = appPackage;
        this.accountUrl = accountUrl;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public String getAccountUrl() {
        return accountUrl;
    }
}
