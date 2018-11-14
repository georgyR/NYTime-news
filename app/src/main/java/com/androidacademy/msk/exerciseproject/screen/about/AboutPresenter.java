package com.androidacademy.msk.exerciseproject.screen.about;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.androidacademy.msk.exerciseproject.App;
import com.androidacademy.msk.exerciseproject.data.network.SocialNetworkApp;
import com.androidacademy.msk.exerciseproject.model.AppError;
import com.androidacademy.msk.exerciseproject.screen.base.BasePresenter;
import com.androidacademy.msk.exerciseproject.utils.IntentUtils;
import com.arellomobile.mvp.InjectViewState;

@InjectViewState
public class AboutPresenter extends BasePresenter<AboutView> {

    private static final String EMAIL = "georgy.ryabykh@gmail.com";
    private static final String PHONE_NUMBER = "+79165766299";

    private final IntentUtils intentUtils;

    public AboutPresenter(@NonNull IntentUtils intentUtils) {
        this.intentUtils = intentUtils;
    }

    public void onSendMessageButtonClicked(@NonNull String message) {
        Intent smsAppIntent = intentUtils.getSmsAppIntent(PHONE_NUMBER, message);
        if (!openApp(smsAppIntent)) {
            getViewState().showErrorSnackbar(AppError.SMS);
        }
    }

    public void onEmailButtonClicked(@NonNull String emailSubject, @NonNull String message) {
        Intent emailIntent = intentUtils.getEmailIntent(EMAIL, emailSubject, message);
        if (!openApp(emailIntent)) {
            getViewState().showErrorSnackbar(AppError.EMAIL);
        }
    }

    public void onTelegramButtonClicked() {
        Intent telegramIntent = intentUtils.getSpecificIntent(SocialNetworkApp.TELEGRAM);
        Intent telegramxIntent = intentUtils.getSpecificIntent(SocialNetworkApp.TELEGRAM_X);
        if (openApp(telegramIntent) || openApp(telegramxIntent)) {
            return;
        }
        Intent browserIntent = intentUtils.getBrowserIntent(SocialNetworkApp.TELEGRAM.getAccountUrl());
        if (!openApp(browserIntent)) {
            getViewState().showErrorSnackbar(AppError.BROWSER);
        }
    }

    public void onInstagramButtonClicked() {
        Intent instagramIntent = intentUtils.getSpecificIntent(SocialNetworkApp.INSTAGRAM);
        if (openApp(instagramIntent)) {
            return;
        }
        Intent browserIntent = intentUtils.getBrowserIntent(SocialNetworkApp.INSTAGRAM.getAccountUrl());
        if (!openApp(browserIntent)) {
            getViewState().showErrorSnackbar(AppError.BROWSER);
        }
    }

    private boolean openApp(@Nullable Intent intent) {
        if (intent != null) {
            getViewState().openApp(intent);
            return true;
        }
        return false;
    }
}
