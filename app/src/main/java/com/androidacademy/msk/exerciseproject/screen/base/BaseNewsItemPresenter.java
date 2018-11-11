package com.androidacademy.msk.exerciseproject.screen.base;

import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;
import com.arellomobile.mvp.MvpView;

public abstract class BaseNewsItemPresenter<T extends MvpView> extends BasePresenter<T>{

    @NonNull
    protected final NewsDao database;

    protected final int itemId;

    public BaseNewsItemPresenter(@NonNull NewsDao dao, int id) {
        database = dao;
        Log.d("INJECT_DEBUG", "NewsDetailsPresenter: " + dao);
        itemId = id;
    }
}
