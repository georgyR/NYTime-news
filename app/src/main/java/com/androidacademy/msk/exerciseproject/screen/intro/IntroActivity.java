package com.androidacademy.msk.exerciseproject.screen.intro;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.screen.news_list.NewsListActivity;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

public class IntroActivity extends MvpAppCompatActivity implements IntroView {

    @InjectPresenter
    IntroPresenter presenter;

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
