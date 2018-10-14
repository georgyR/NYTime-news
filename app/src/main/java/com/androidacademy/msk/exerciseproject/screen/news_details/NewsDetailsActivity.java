package com.androidacademy.msk.exerciseproject.screen.news_details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.Utils.DataUtils;
import com.androidacademy.msk.exerciseproject.Utils.DateUtils;
import com.androidacademy.msk.exerciseproject.data.model.NewsItem;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_POSITION = "EXTRA_POSITION";

    private ImageView imageView;
    private TextView titleTextView;
    private TextView publishDateTextView;
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

        Toolbar toolbar = findViewById(R.id.activity_news_details__toolbar);
        setSupportActionBar(toolbar);
        setupBackToolbarButton(toolbar);

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

    private void setupBackToolbarButton(@NonNull Toolbar toolbar) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setColorFilter(
                    getResources().getColor(R.color.white),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }
}
