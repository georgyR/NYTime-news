package com.androidacademy.msk.exerciseproject.screen.intro;

import com.androidacademy.msk.exerciseproject.data.Storage;
import com.androidacademy.msk.exerciseproject.screen.base.BasePresenter;
import com.arellomobile.mvp.InjectViewState;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;

@InjectViewState
public class IntroPresenter extends BasePresenter<IntroView> {

    private static final int TIMEOUT = 3;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        boolean isIntroVisible = new Storage().needToShowIntro();
        if (isIntroVisible) {
            getViewState().setLayout();
            compositeDisposable.add(Completable.complete()
                    .delay(TIMEOUT, TimeUnit.SECONDS)
                    .subscribe(() -> getViewState().startNewsListActivity()));
        } else {
            getViewState().startNewsListActivity();
        }
    }
}
