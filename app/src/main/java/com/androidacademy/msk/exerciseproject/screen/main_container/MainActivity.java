package com.androidacademy.msk.exerciseproject.screen.main_container;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.screen.news_details.NewsDetailsFragment;
import com.androidacademy.msk.exerciseproject.screen.news_list.NewsListFragment;

public class MainActivity extends AppCompatActivity
        implements NewsListFragment.ItemClickListener,
        FragmentContainer {

    private static final String TAG_LIST = "TAG_LIST";
    private static final String TAG_DETAILS = "TAG_DETAILS";
    public boolean isTwoPanel;
    private FragmentManager fragmentManager;

    public static Intent getStartIntent(@NonNull Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        isTwoPanel = findViewById(R.id.framelayout_main_details) != null;
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.framelayout_main_list, NewsListFragment.newInstance(), TAG_LIST)
                    .addToBackStack(null)
                    .commit();
            return;
        }

        Fragment detailsFragment = fragmentManager.findFragmentByTag(TAG_DETAILS);
        if (isTwoPanel) {
            if (detailsFragment != null) {
                fragmentManager.popBackStackImmediate();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout_main_details, detailsFragment, TAG_DETAILS)
                        .addToBackStack(null)
                        .commit();
            }
        } else {
            if (detailsFragment != null) {
                fragmentManager.popBackStackImmediate();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout_main_list, detailsFragment, TAG_DETAILS)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isTwoPanel) {
            finish();
        }
        if (fragmentManager.getBackStackEntryCount() <= 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onNewItemClicked(int id) {
        Fragment detailsFragment = fragmentManager.findFragmentByTag(TAG_DETAILS);
        if (detailsFragment != null) {
            fragmentManager.popBackStackImmediate();
        }
        int detailsFrameId = isTwoPanel ? R.id.framelayout_main_details : R.id.framelayout_main_list;
        getSupportFragmentManager().beginTransaction()
                .replace(detailsFrameId, NewsDetailsFragment.newInstance(id), TAG_DETAILS)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean isTwoPanel() {
        return isTwoPanel;
    }

    @Override
    public void setSpinnerVisibility(int visibility) {
        NewsListFragment listFragment = (NewsListFragment) fragmentManager.findFragmentByTag(TAG_LIST);
        if (listFragment != null) {
            listFragment.setSpinnerVisibility(visibility);
        }
    }

    @NonNull
    @Override
    public String getCurrentSelectedSection() {
        NewsListFragment listFragment = (NewsListFragment) fragmentManager.findFragmentByTag(TAG_LIST);
        if (listFragment != null) {
            return listFragment.getCurrentSelectedSection();
        }
        return "";
    }
}
