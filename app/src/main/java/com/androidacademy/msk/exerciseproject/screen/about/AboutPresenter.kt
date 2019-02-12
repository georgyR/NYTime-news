package com.androidacademy.msk.exerciseproject.screen.about

import com.androidacademy.msk.exerciseproject.screen.base.BasePresenter
import com.arellomobile.mvp.InjectViewState

@InjectViewState
class AboutPresenter : BasePresenter<AboutView>() {

    fun onSendMessageButtonClicked() {
        viewState.openSmsApp()
    }

    fun onEmailButtonClicked() {
        viewState.openEmailApp()
    }

    fun onTelegramButtonClicked() {
        viewState.openTelegramApp()
    }

    fun onInstagramButtonClicked() {
        viewState.openIstagramApp()
    }
}
