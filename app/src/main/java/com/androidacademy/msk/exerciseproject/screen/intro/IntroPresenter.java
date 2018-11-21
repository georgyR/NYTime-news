package com.androidacademy.msk.exerciseproject.screen.intro;

import com.androidacademy.msk.exerciseproject.data.Storage;
import com.androidacademy.msk.exerciseproject.screen.base.BasePresenter;
import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

@InjectViewState
public class IntroPresenter extends BasePresenter<IntroView> {

    private final Storage storage;

    @Inject
    public IntroPresenter(Storage storage) {
        this.storage = storage;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        boolean isIntroVisible = storage.isFirstAppLaunch();
        if (isIntroVisible) {
            getViewState().setLayout();
        } else {
            getViewState().startNewsListActivity();
        }
    }
}
