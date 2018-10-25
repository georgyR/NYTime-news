package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.data.Section;
import com.androidacademy.msk.exerciseproject.data.model.NewsItem;
import com.androidacademy.msk.exerciseproject.screen.about.AboutActivity;
import com.androidacademy.msk.exerciseproject.screen.news_details.NewsDetailsActivity;
import com.androidacademy.msk.exerciseproject.utils.DataUtils;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class NewsListActivity extends MvpAppCompatActivity implements NewsListView {

    private static final int MIN_WIDTH_IN_DP = 300;
    private static final String LIST_STATE_KEY = "LIST_STATE_KEY";

    @NonNull
    private RecyclerView recyclerView;
    @NonNull
    private ProgressBar progressBar;
    @NonNull
    private View errorView;
    @NonNull
    private Button tryAgainButton;
    @NonNull
    private Spinner spinner;
    @NonNull
    private RecyclerView.LayoutManager layoutManager;
    @Nullable
    private Parcelable listState;
    @NonNull
    private NewsAdapter adapter;


    @InjectPresenter
    public NewsListPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        Toolbar toolbar = findViewById(R.id.all_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar = findViewById(R.id.activity_news_list__progressbar);

        recyclerView = findViewById(R.id.activity_news_list__recycler_view);
        setupRecyclerView(recyclerView);

        errorView = findViewById(R.id.activity_news_list__view_error);

        tryAgainButton = findViewById(R.id.view_error__button_try_again);
        tryAgainButton.setOnClickListener(v -> presenter.onTryAgainButtonClicked());

        spinner = findViewById(R.id.activity_news_list__spinner);
        setupSpinner(spinner);
    }

    @Override
    protected void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listState != null) {
            layoutManager.onRestoreInstanceState(listState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.activity_news_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_open_about:
                startActivity(AboutActivity.getStartIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        listState = layoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, listState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showNews(@NonNull List<NewsItem> news) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);

        adapter.addListData(news);
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void showError() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void openDetailsScreen(int position) {
        startActivity(NewsDetailsActivity.getStartIntent(position, this));
    }

    private void setLayoutManager(@NonNull RecyclerView recyclerView) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidthInDp = displayMetrics.widthPixels / displayMetrics.density;

        if (screenWidthInDp < MIN_WIDTH_IN_DP) {
            layoutManager = new LinearLayoutManager(this);
        } else {
            int snapCount = (int) (screenWidthInDp / MIN_WIDTH_IN_DP);
            layoutManager = new StaggeredGridLayoutManager(
                    snapCount,
                    StaggeredGridLayoutManager.VERTICAL);
        }
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setItemDecoration(@NonNull RecyclerView recyclerView) {
        DividerItemDecoration decoration
                = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        if (dividerDrawable != null) {
            decoration.setDrawable(dividerDrawable);
        }
        recyclerView.addItemDecoration(decoration);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        setLayoutManager(recyclerView);
        setItemDecoration(recyclerView);
        NewsAdapter.OnItemClickListener clickListener = position ->
                presenter.onItemClicked(position);
        adapter = new NewsAdapter(clickListener, this);
        recyclerView.setAdapter(adapter);
    }

    private void setupSpinner(@NonNull Spinner spinner) {
        List<String> spinnerList = DataUtils.convertEmunValuesToList(Section.values());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onSpinnerItemClicked(spinnerList.get(position).toLowerCase());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
