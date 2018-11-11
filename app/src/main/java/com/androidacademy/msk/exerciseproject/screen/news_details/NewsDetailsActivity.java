package com.androidacademy.msk.exerciseproject.screen.news_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidacademy.msk.exerciseproject.App;
import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem;
import com.androidacademy.msk.exerciseproject.screen.news_editor.NewsEditorActivity;
import com.androidacademy.msk.exerciseproject.utils.DateUtils;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;

public class NewsDetailsActivity extends MvpAppCompatActivity implements NewsDetailsView {

    private static final String DEBUG_OPTION_ITEM = NewsDetailsActivity.class.getSimpleName();

    private static final String EXTRA_ID = "EXTRA_ID";

    public static final int RESULT_NEWS_IS_DELETED = 20;
    private static final int CHANGE_NEWS_REQUEST = 21;
    public static final int RESULT_NEWS_IS_CHANGED = 22;

    @NonNull
    private TextView titleTextView;
    @NonNull
    private ImageView imageView;
    @NonNull
    private TextView abstractTextView;
    @NonNull
    private TextView dateTextView;
    @NonNull
    private TextView timeTextView;

    @InjectPresenter
    public NewsDetailsPresenter presenter;

    @ProvidePresenter
    public NewsDetailsPresenter providePresenter() {
        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        return new NewsDetailsPresenter(id);
    }

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

        Toolbar toolbar = findViewById(R.id.toolbar_all);
        setupToolbar(toolbar);

        titleTextView = findViewById(R.id.textview_newsdetails_title);
        imageView = findViewById(R.id.imageview_newsdetails);
        abstractTextView = findViewById(R.id.textview_newsdetails_abstract);
        dateTextView = findViewById(R.id.textview_newsdetails_date);
        timeTextView = findViewById(R.id.textview_newsdetails_time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_news_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_edit_news:
                presenter.onEditNewsOptionItemSelected();
                break;
            case R.id.menuitem_delete_news:
                presenter.onDeleteOptionsItemSelected();
                setResult(RESULT_NEWS_IS_DELETED);
                finish();
                break;
            default:
                Log.d(DEBUG_OPTION_ITEM, "Unknown menu item id");
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CHANGE_NEWS_REQUEST &&
                resultCode == RESULT_OK) {
            presenter.onNewsEdited();
            setResult(NewsDetailsActivity.RESULT_NEWS_IS_CHANGED);
        }

    }

    @Override
    public void showNewsDetails(@NonNull DbNewsItem newsItem) {
        setTitle(newsItem.getSection());
        titleTextView.setText(newsItem.getTitle());
        Glide.with(this).load(newsItem.getFullsizeImageUrl()).into(imageView);
        abstractTextView.setText(newsItem.getAbstractX());
        String publishedDate = newsItem.getPublishedDate();
        if (publishedDate != null) {
            String date = DateUtils.getFormattedDate(publishedDate);
            dateTextView.setText(date);
            String time = DateUtils.getFormattedTime(publishedDate, this);
            timeTextView.setText(time);
        }
    }

    @Override
    public void openEditorActivity(int itemId) {
        startActivityForResult(
                NewsEditorActivity.getStartIntent(itemId, this),
                CHANGE_NEWS_REQUEST);
    }

    private void setupToolbar(@NonNull Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}
