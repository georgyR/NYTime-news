package com.androidacademy.msk.exerciseproject.screen.about;

import com.androidacademy.msk.exerciseproject.screen.base.BasePresenter;
import com.arellomobile.mvp.InjectViewState;

@InjectViewState
public class AboutPresenter extends BasePresenter<AboutView> {

    public void onSendMessageButtonClicked() {
        getViewState().openSmsApp();
    }

    public void onEmailButtonClicked() {
        getViewState().openEmailApp();
    }

    public void onTelegramButtonClicked() {
        getViewState().openTelegramApp();
    }

    public void onInstagramButtonClicked() {
        getViewState().openIstagramApp();
    }
}
