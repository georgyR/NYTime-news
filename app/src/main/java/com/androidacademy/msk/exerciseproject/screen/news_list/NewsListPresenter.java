package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.database.NewsConverter;
import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem;
import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApi;
import com.androidacademy.msk.exerciseproject.model.Section;
import com.androidacademy.msk.exerciseproject.screen.base.BasePresenter;
import com.androidacademy.msk.exerciseproject.utils.EnumUtils;
import com.androidacademy.msk.exerciseproject.utils.NewsDataUtils;
import com.androidacademy.msk.exerciseproject.utils.StringUtils;
import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsListPresenter extends BasePresenter<NewsListView> {

    @NonNull
    private final NYTimesApi api;
    @NonNull
    private final NewsDao database;

    @NonNull
    private Section currentSelectedSection = Section.HOME;

    @Inject
    public NewsListPresenter(@NonNull NYTimesApi api, @NonNull NewsDao database) {
        this.api = api;
        this.database = database;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showEmptyView();
    }

    public void onItemClicked(int id) {
        getViewState().openDetailsScreen(id);
    }

    public void onTryAgainButtonClicked() {
        getNews(currentSelectedSection.toString().toLowerCase());
    }

    public void onFabClicked() {
        getNews(currentSelectedSection.toString().toLowerCase());
    }

    public void onSpinnerItemClicked(@NonNull Section section) {
        if (!section.equals(currentSelectedSection)) {
            currentSelectedSection = section;
            getNews(section.name().toLowerCase());
        }
    }

    public void onSetupSpinnerPosition() {
        List<String> categoryList = EnumUtils.convertEnumValuesToCapitalizedList(Section.values());
        int position = categoryList.indexOf(StringUtils.capitalize(currentSelectedSection.name()));
        getViewState().setCurrentSectionInSpinner(position);

    }

    private void getNews(@NonNull String section) {
        compositeDisposable.add(
                api.getNews(section)
                        .map(newsResponse -> {
                            List<DbNewsItem> dbNewsItems = NewsConverter.toDatabase(
                                    newsResponse.getResults(),
                                    section);
                            database.deleteBySection(section);
                            database.insertAll(dbNewsItems);
                            int[] ids = database.getNewsIdBySection(section);
                            return NewsDataUtils.setIds(dbNewsItems, ids);
                        })
                        .onErrorReturn(throwable -> {
                            List<DbNewsItem> news = database.getNewsBySection(section);
                            if (news.isEmpty()) {
                                return null;
                            }
                            return news;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showProgressBar())
                        .subscribe(
                                news -> getViewState().showNews(news),
                                throwable -> getViewState().showError())
        );
    }
}
