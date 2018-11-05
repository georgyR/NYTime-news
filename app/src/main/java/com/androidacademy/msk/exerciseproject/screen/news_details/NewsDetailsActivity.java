package com.androidacademy.msk.exerciseproject.screen.news_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.androidacademy.msk.exerciseproject.screen.ViewVisibilitySwitcher;
import com.androidacademy.msk.exerciseproject.utils.DateUtils;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;

public class NewsDetailsActivity extends MvpAppCompatActivity implements NewsDetailsView {

    private static final String EXTRA_ID = "EXTRA_ID";

    @NonNull
    private TextView titleTextView;
    @NonNull
    private ImageView imageView;
    @NonNull
    private TextView abstractTextView;
    @NonNull
    private TextView dateTextView;

    @InjectPresenter
    public NewsDetailsPresenter presenter;

    @NonNull
    public static Intent getStartIntent(int id, @NonNull Context context) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Toolbar toolbar = findViewById(R.id.all_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        presenter.onCreateActivity(id);

        titleTextView = findViewById(R.id.activity_news_details__title);
        imageView = findViewById(R.id.activity_news_details__image);
        abstractTextView = findViewById(R.id.activity_news_details__abstract);
        dateTextView = findViewById(R.id.activity_news_details__date);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showNewsDetails(DbNewsItem newsItem) {
        setTitle(newsItem.getSection());
        titleTextView.setText(newsItem.getTitle());
        Glide.with(this).load(newsItem.getFullsizeImageUrl()).into(imageView);
        abstractTextView.setText(newsItem.getAbstractX());
        String publishedDate = newsItem.getPublishedDate();
        if (publishedDate != null) {
            String date = DateUtils.convertTimestampToString(publishedDate);
            dateTextView.setText(date);
        }
    }
}
