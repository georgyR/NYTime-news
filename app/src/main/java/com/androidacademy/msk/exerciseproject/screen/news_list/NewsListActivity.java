package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.androidacademy.msk.exerciseproject.data.model.NewsItem;
import com.androidacademy.msk.exerciseproject.screen.news_details.NewsDetailsActivity;
import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.Utils.DataUtils;
import com.androidacademy.msk.exerciseproject.screen.about.AboutActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListActivity extends AppCompatActivity {

    private static final int MIN_WIDTH_IN_DP = 300;
    private static final String TAG = "thread_debug";
    private static final String LIST_STATE_KEY = "LIST_STATE_KEY";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Disposable disposable;
    private RecyclerView.LayoutManager layoutManager;
    private Parcelable listState;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        Toolbar toolbar = findViewById(R.id.all_toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.activity_news_list__progressbar);

        recyclerView = findViewById(R.id.activity_news_list__recycler_view);
        setupRecyclerView(recyclerView);

        NewsAdapter.OnItemClickListener clickListener = position ->
                startActivity(NewsDetailsActivity.getStartIntent(position, this));

        loadNews(clickListener);
    }

    @Override
    protected void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
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
        outState.putParcelable(LIST_STATE_KEY, layoutManager.onSaveInstanceState());
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposable.dispose();

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
    }

    private void loadNews (@NonNull NewsAdapter.OnItemClickListener clickListener) {
        disposable = Observable
                .create((ObservableOnSubscribe<List<NewsItem>>) emitter -> {
                    Log.d(TAG, "loading news: " + Thread.currentThread());
                    Thread.sleep(2000);
                    emitter.onNext(DataUtils.NEWS);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    Log.d(TAG, "doOnSubscribe: " + Thread.currentThread());
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                })
                .subscribe(newsItems -> {
                    Log.d(TAG, "subscribe: " + Thread.currentThread());
                    recyclerView.setAdapter(new NewsAdapter(newsItems, clickListener, this));
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                });
    }

}
