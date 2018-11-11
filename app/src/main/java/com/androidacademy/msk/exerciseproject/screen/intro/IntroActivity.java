package com.androidacademy.msk.exerciseproject.screen.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.data.Storage;
import com.androidacademy.msk.exerciseproject.screen.news_list.NewsListActivity;

import java.util.concurrent.TimeUnit;

public class IntroActivity extends AppCompatActivity {


    private static final String DEBUG_THREAD_SLEEP = IntroActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isIntroVisible = Storage.needToShowIntro(this);
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
                Log.d(DEBUG_THREAD_SLEEP, "Sleep in main thread", e);
            }
        }).start();
    }
}
