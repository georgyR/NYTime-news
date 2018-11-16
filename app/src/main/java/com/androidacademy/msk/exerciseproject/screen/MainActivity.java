package com.androidacademy.msk.exerciseproject.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.di.module.NewsDetailsModule;
import com.androidacademy.msk.exerciseproject.screen.news_details.NewsDetailsFragment;
import com.androidacademy.msk.exerciseproject.screen.news_list.NewsListFragment;

public class MainActivity extends AppCompatActivity implements NewsListFragment.ItemClickListener {

    public static Intent getStartIntent(@NonNull Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout_container, NewsListFragment.newInstance())
                    .commit();
        }

    }

    @Override
    public void onNewItemClicked(int id) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout_container, NewsDetailsFragment.newInstance(id))
                .addToBackStack(null)
                .commit();
    }
}
