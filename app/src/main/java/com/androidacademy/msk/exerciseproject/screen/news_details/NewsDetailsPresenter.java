package com.androidacademy.msk.exerciseproject.screen.news_details;

import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;
import com.androidacademy.msk.exerciseproject.screen.base.BaseNewsItemPresenter;
import com.arellomobile.mvp.InjectViewState;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsDetailsPresenter extends BaseNewsItemPresenter<NewsDetailsView> {

    private static final String DEBUG_DB_QUERY = NewsDetailsPresenter.class.getSimpleName();

    public NewsDetailsPresenter(@NonNull NewsDao dao, int id) {
        super(dao, id);
    }

    /*@NonNull
    private final NewsDao database;

    private final int itemId;

    *//*public NewsDetailsPresenter(@NonNull NewsDao dao, int id) {
        database = dao;
        Log.d("INJECT_DEBUG", "NewsDetailsPresenter: " + dao);
        itemId = id;
    }*/

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getNewsDetails();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onNewsEdited() {
        getNewsDetails();
    }

    public void onDeleteOptionsItemSelected() {
        compositeDisposable.add(Completable.complete()
                .observeOn(Schedulers.io())
                .subscribe(() -> database.deleteNewsItemById(itemId))
        );
    }

    private void getNewsDetails() {
        compositeDisposable.add(database.getRxNewsById(itemId)
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

    public void onEditNewsOptionItemSelected() {
        getViewState().openEditorActivity(itemId);
    }
}