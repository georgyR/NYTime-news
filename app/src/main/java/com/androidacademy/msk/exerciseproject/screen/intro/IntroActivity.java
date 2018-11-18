package com.androidacademy.msk.exerciseproject.screen.intro;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.di.Injector;
import com.androidacademy.msk.exerciseproject.screen.news_list.NewsListActivity;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

public class IntroActivity extends MvpAppCompatActivity implements IntroView {

    @Inject
    @InjectPresenter
    IntroPresenter presenter;

    @ProvidePresenter
    IntroPresenter providePresenter() {
        Injector.getInstance().getAppComponent().inject(this);
        return presenter;
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_intro);
    }

    @Override
    public void startNewsListActivity() {
        startActivity(NewsListActivity.getStartIntent(this));
        finish();
    }
}
