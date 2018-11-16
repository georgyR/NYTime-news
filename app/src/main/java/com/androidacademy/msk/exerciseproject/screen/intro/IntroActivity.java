package com.androidacademy.msk.exerciseproject.screen.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.di.Injector;
import com.androidacademy.msk.exerciseproject.screen.MainActivity;
import com.androidacademy.msk.exerciseproject.screen.news_list.NewsListFragment;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import me.relex.circleindicator.CircleIndicator;

public class IntroActivity extends MvpAppCompatActivity implements IntroView {

    @Inject
    @InjectPresenter
    IntroPresenter presenter;

    @ProvidePresenter
    IntroPresenter providePresenter() {
        Injector.getInstance(getApplicationContext()).getIntroComponent().inject(this);
        return presenter;
    }

    /*@Override
    public void setLayout() {
        setContentView(R.layout.activity_intro);
    }*/

    /*@Override
    public void startNewsListActivity() {
        startActivity(NewsListActivity.getStartIntent(this));
        finish();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ViewPager viewPager = findViewById(R.id.viewpager_intro);
        IntroPagerAdapter adapter = new IntroPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        CircleIndicator circleIndicator = findViewById(R.id.indicator_intro);
        circleIndicator.setViewPager(viewPager);

        Button getStartedButton = findViewById(R.id.button_intro_get_started);
        getStartedButton.setOnClickListener(v -> {
            startActivity(MainActivity.getStartIntent(this));
            finish();
        });
    }

    private static class IntroPagerAdapter extends FragmentPagerAdapter {

        private static final int NUM_PAGES = 3;

        public IntroPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int drawableRes;
            switch (position) {
                case 0:
                    drawableRes = R.drawable.device_screenshot_newslist;
                    break;
                case 1:
                    drawableRes = R.drawable.device_screenshot_details;
                    break;
                case 2:
                    drawableRes = R.drawable.device_screenshot_about;
                    break;
                default:
                    return null;
            }
            return PageFragment.newInstance(drawableRes);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
