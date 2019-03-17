package com.androidacademy.msk.exerciseproject.screen.intro

import com.androidacademy.msk.exerciseproject.data.network.shared_preferences.Storage
import com.androidacademy.msk.exerciseproject.screen.base.BasePresenter
import com.arellomobile.mvp.InjectViewState
import javax.inject.Inject

@InjectViewState
class IntroPresenter @Inject constructor(private val storage: Storage) : BasePresenter<IntroView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val isIntroVisible = storage.isFirstAppLaunch()
        if (isIntroVisible) {
            viewState.setLayout()
        } else {
            viewState.startNewsListActivity()
        }
    }
}
