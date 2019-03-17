package com.androidacademy.msk.exerciseproject.di.module

import com.androidacademy.msk.exerciseproject.data.network.shared_preferences.Storage
import com.androidacademy.msk.exerciseproject.screen.intro.IntroPresenter
import toothpick.config.Module


class IntroModule : Module() {
    init {
        bind(IntroPresenter::class.java).singletonInScope()
        bind(Storage::class.java).singletonInScope()
    }
}