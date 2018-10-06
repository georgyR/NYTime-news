package com.androidacademy.msk.exerciseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        NewsItem newsItem = DataUtils.getNews().get(position);

        setTitle(newsItem.getCategory().getName());

        imageView = findViewById(R.id.news_image);
        titleTextView = findViewById(R.id.tv_title);
        publishDateTextView = findViewById(R.id.tv_publish_date);
        fullTextView = findViewById(R.id.tv_full_text);

        Picasso.get().load(newsItem.getImageUrl()).into(imageView);
        titleTextView.setText(newsItem.getTitle());
        fullTextView.setText(newsItem.getFullText());
        String publishDate = DataUtils.convertDateToString(newsItem.getPublishDate());
        publishDateTextView.setText(publishDate);
    }

    public static Intent createIntent(int position, Context context) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }
}
