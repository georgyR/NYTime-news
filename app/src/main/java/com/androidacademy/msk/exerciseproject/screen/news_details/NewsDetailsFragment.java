package com.androidacademy.msk.exerciseproject.screen.news_details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem;
import com.androidacademy.msk.exerciseproject.di.Injector;
import com.androidacademy.msk.exerciseproject.screen.news_editor.NewsEditorActivity;
import com.androidacademy.msk.exerciseproject.utils.DateUtils;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

public class NewsDetailsFragment extends MvpAppCompatFragment implements NewsDetailsView {

    private static final String DEBUG_OPTION_ITEM = NewsDetailsFragment.class.getSimpleName();

    private static final String KEY_ID = "KEY_ID";

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

    @Inject
    @InjectPresenter
    public NewsDetailsPresenter presenter;

    @ProvidePresenter
    public NewsDetailsPresenter providePresenter() {
        int id = getArguments().getInt(KEY_ID);
        Injector.getInstance(getActivity().getApplicationContext()).getNewsDetailsComponent(id).inject(this);
        return presenter;
    }

    public static NewsDetailsFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(KEY_ID, id);

        NewsDetailsFragment fragment = new NewsDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar_all);
        setupToolbar(toolbar);

        titleTextView = view.findViewById(R.id.textview_newsdetails_title);
        imageView = view.findViewById(R.id.imageview_newsdetails);
        abstractTextView = view.findViewById(R.id.textview_newsdetails_abstract);
        dateTextView = view.findViewById(R.id.textview_newsdetails_date);
        timeTextView = view.findViewById(R.id.textview_newsdetails_time);

        return view;
    }


   /* @Override
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
            setResult(NewsDetailsFragment.RESULT_NEWS_IS_CHANGED);
        }

    }*/

    @Override
    public void showNewsDetails(@NonNull DbNewsItem newsItem) {
        getActivity().setTitle(newsItem.getSection());
        titleTextView.setText(newsItem.getTitle());
        if (newsItem.getFullsizeImageUrl() != null) {
            Glide.with(this).load(newsItem.getFullsizeImageUrl()).into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }
        abstractTextView.setText(newsItem.getAbstractX());
        String publishedDate = newsItem.getPublishedDate();
        if (publishedDate != null) {
            String date = DateUtils.getFormattedDate(publishedDate);
            dateTextView.setText(date);
            String time = DateUtils.getFormattedTime(publishedDate, getContext());
            timeTextView.setText(time);
        }
    }

    @Override
    public void openEditorActivity(int itemId) {
        startActivityForResult(
                NewsEditorActivity.getStartIntent(itemId, getContext()),
                CHANGE_NEWS_REQUEST);
    }

    private void setupToolbar(@NonNull Toolbar toolbar) {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }
}
