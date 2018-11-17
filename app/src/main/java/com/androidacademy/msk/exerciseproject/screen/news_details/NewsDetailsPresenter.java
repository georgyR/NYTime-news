package com.androidacademy.msk.exerciseproject.screen.news_details;

import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;
import com.androidacademy.msk.exerciseproject.screen.base.BaseNewsItemPresenter;
import com.arellomobile.mvp.InjectViewState;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsDetailsPresenter extends BaseNewsItemPresenter<NewsDetailsView> {

    private static final String DEBUG_DB_QUERY = NewsDetailsPresenter.class.getSimpleName();

    public NewsDetailsPresenter(@NonNull NewsDao dao, int id) {
        super(dao, id);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getNewsDetails();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getNewsDetails() {
        compositeDisposable.add(
                dao.getRxNewsById(itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newsItem -> getViewState().showNewsDetails(newsItem),
                        throwable -> Log.d(
                                DEBUG_DB_QUERY,
                                "error in getRxNewsById() query",
                                throwable))
        );
    }
}
