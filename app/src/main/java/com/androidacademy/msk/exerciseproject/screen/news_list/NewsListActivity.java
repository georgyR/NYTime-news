package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.androidacademy.msk.exerciseproject.network.api.Section;
import com.androidacademy.msk.exerciseproject.network.model.NetworkNewsItem;
import com.androidacademy.msk.exerciseproject.screen.ViewVisibilitySwitcher;
import com.androidacademy.msk.exerciseproject.screen.about.AboutActivity;
import com.androidacademy.msk.exerciseproject.screen.news_details.NewsDetailsActivity;
import com.androidacademy.msk.exerciseproject.utils.EnumUtils;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import static com.androidacademy.msk.exerciseproject.screen.UiState.EMPTY;
import static com.androidacademy.msk.exerciseproject.screen.UiState.ERROR;
import static com.androidacademy.msk.exerciseproject.screen.UiState.HAS_DATA;
import static com.androidacademy.msk.exerciseproject.screen.UiState.LOADING;

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
    private View emptyListView;
    @NonNull
    private Button tryAgainButton;
    @NonNull
    private FloatingActionButton fab;
    @NonNull
    private Spinner spinner;
    @NonNull
    private RecyclerView.LayoutManager layoutManager;
    @Nullable
    private Parcelable listState;
    @NonNull
    private NewsAdapter adapter;
    @NonNull
    private ViewVisibilitySwitcher visibilitySwitcher;

    @InjectPresenter
    public NewsListPresenter presenter;

    public static Intent getStartIntent(@NonNull Context context) {
        return new Intent(context, NewsListActivity.class);
    }

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

        emptyListView = findViewById(R.id.activity_news_list_view_empty_list);

        visibilitySwitcher = new ViewVisibilitySwitcher(
                recyclerView,
                progressBar,
                errorView,
                emptyListView);

        tryAgainButton = findViewById(R.id.view_error__button_try_again);
        tryAgainButton.setOnClickListener(v -> presenter.onTryAgainButtonClicked());

        fab = findViewById(R.id.activity_news_list_fab);
        fab.setOnClickListener(v -> presenter.onFabClicked());

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
    public void showNews(@NonNull List<DbNewsItem> news) {
        visibilitySwitcher.setUiState(HAS_DATA);

        adapter.addListData(news);
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void showError() {
        visibilitySwitcher.setUiState(ERROR);
    }

    @Override
    public void showEmptyView() {
        visibilitySwitcher.setUiState(EMPTY);
    }

    @Override
    public void showProgressBar() {
        visibilitySwitcher.setUiState(LOADING);
    }

    @Override
    public void openDetailsScreen(int id) {
        startActivity(NewsDetailsActivity.getStartIntent(id, this));
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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy){
                if (dy > 0)
                    fab.hide();
                else if (dy < 0)
                    fab.show();
            }
        });

        setLayoutManager(recyclerView);
        setItemDecoration(recyclerView);
        NewsAdapter.OnItemClickListener clickListener = id -> presenter.onItemClicked(id);
        adapter = new NewsAdapter(clickListener, this);
        recyclerView.setAdapter(adapter);
    }

    private void setupSpinner(@NonNull Spinner spinner) {
        List<String> spinnerList = EnumUtils.convertEnumValuesToList(Section.values());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onSpinnerItemClicked(Section.values()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
