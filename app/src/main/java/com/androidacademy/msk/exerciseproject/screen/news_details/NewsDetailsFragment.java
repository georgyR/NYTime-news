package com.androidacademy.msk.exerciseproject.screen.news_details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem;
import com.androidacademy.msk.exerciseproject.di.Injector;
import com.androidacademy.msk.exerciseproject.utils.DateUtils;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

public class NewsDetailsFragment extends MvpAppCompatFragment implements NewsDetailsView {

    private static final String KEY_ID = "KEY_ID";

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
        Injector.getInstance().getNewsItemComponent(id).inject(this);
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);

        titleTextView = view.findViewById(R.id.textview_newsdetails_title);
        imageView = view.findViewById(R.id.imageview_newsdetails);
        abstractTextView = view.findViewById(R.id.textview_newsdetails_abstract);
        dateTextView = view.findViewById(R.id.textview_newsdetails_date);
        timeTextView = view.findViewById(R.id.textview_newsdetails_time);

        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void showNewsDetails(@NonNull DbNewsItem newsItem) {
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
}
