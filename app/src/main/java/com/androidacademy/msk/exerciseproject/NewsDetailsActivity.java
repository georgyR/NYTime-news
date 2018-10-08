package com.androidacademy.msk.exerciseproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidacademy.msk.exerciseproject.data.DataUtils;
import com.androidacademy.msk.exerciseproject.data.NewsItem;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_POSITION = "EXTRA_POSITION";
    private ImageView imageView;
    private TextView titleTextView;
    private TextView publishDateTextView;
    private TextView fullTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupBackToolbarButton(toolbar);

        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        NewsItem newsItem = DataUtils.NEWS.get(position);

        setTitle(newsItem.getCategory().getName());

        imageView = findViewById(R.id.news_image);
        Picasso.get().load(newsItem.getImageUrl()).into(imageView);

        titleTextView = findViewById(R.id.tv_title);
        titleTextView.setText(newsItem.getTitle());

        publishDateTextView = findViewById(R.id.tv_publish_date);
        String publishDate = DataUtils.convertDateToString(newsItem.getPublishDate());
        publishDateTextView.setText(publishDate);

        fullTextView = findViewById(R.id.tv_full_text);
        fullTextView.setText(newsItem.getFullText());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupBackToolbarButton(@NonNull Toolbar toolbar) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(
                getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);
    }

    public static Intent getStartIntent(int position, @NonNull Context context) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }
}
