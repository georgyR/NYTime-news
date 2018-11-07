package com.androidacademy.msk.exerciseproject.screen.intro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.App;
import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.screen.news_list.NewsListActivity;

import java.util.concurrent.TimeUnit;

public class IntroActivity extends AppCompatActivity {

    private static final String SHARED_PREF = "SHARED_PREF";
    private static final String IS_INTRO_VISIBLE_KEY = "IS_INTRO_VISIBLE_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        boolean isIntroVisible = sharedPreferences.getBoolean(IS_INTRO_VISIBLE_KEY, true);

        sharedPreferences.edit()
                .putBoolean(IS_INTRO_VISIBLE_KEY, !isIntroVisible)
                .apply();

        if (isIntroVisible) {
            setContentView(R.layout.activity_intro);
        } else {
            startActivity(NewsListActivity.getStartIntent(this));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                startActivity(NewsListActivity.getStartIntent(this));
                finish();
            } catch (InterruptedException e) {
                Log.d(App.UI_DEBUG_TAG, "Sleep in main thread", e);
            }
        }).start();
    }
}
