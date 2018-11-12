package com.androidacademy.msk.exerciseproject.screen.base;

import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;
import com.arellomobile.mvp.MvpView;

public abstract class BaseNewsItemPresenter<T extends MvpView> extends BasePresenter<T>{

    private static final String DEBUG_INJECT = "DEBUG_INJECT";

    @NonNull
    protected final NewsDao dao;

    protected final int itemId;

    public BaseNewsItemPresenter(@NonNull NewsDao dao, int id) {
        this.dao = dao;
        Log.d(DEBUG_INJECT, "NewsDetailsPresenter: " + dao);
        itemId = id;
    }
}
