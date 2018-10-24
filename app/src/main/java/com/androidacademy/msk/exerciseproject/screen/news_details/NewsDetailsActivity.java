package com.androidacademy.msk.exerciseproject.screen.news_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.utils.DataUtils;
import com.androidacademy.msk.exerciseproject.utils.DateUtils;
import com.androidacademy.msk.exerciseproject.data.model.NewsItem;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_POSITION = "EXTRA_POSITION";

    @NonNull
    private ImageView imageView;
    @NonNull
    private TextView titleTextView;
    @NonNull
    private TextView publishDateTextView;
    @NonNull
    private TextView fullTextView;

    @NonNull
    public static Intent getStartIntent(int position, @NonNull Context context) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
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

        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        NewsItem newsItem = DataUtils.NEWS.get(position);

        setTitle(newsItem.getCategory().getName());

        imageView = findViewById(R.id.activity_news_details__imageview_news);
        Picasso.get().load(newsItem.getImageUrl()).into(imageView);

        titleTextView = findViewById(R.id.activity_news_details__textview_title);
        titleTextView.setText(newsItem.getTitle());

        publishDateTextView = findViewById(R.id.activity_news_details__textview_publish_date);
        String publishDate = DateUtils.convertDateToString(newsItem.getPublishDate(), this);
        publishDateTextView.setText(publishDate);

        fullTextView = findViewById(R.id.activity_news_details__textview_full_text);
        fullTextView.setText(newsItem.getFullText());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
