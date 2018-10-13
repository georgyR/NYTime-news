package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.androidacademy.msk.exerciseproject.screen.news_details.NewsDetailsActivity;
import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.Utils.DataUtils;
import com.androidacademy.msk.exerciseproject.screen.about.AboutActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class NewsListActivity extends AppCompatActivity {

    private static final int MIN_WIDTH_IN_DP = 300;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        Toolbar toolbar = findViewById(R.id.all_toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.activity_news_list__recycler_view);
        setupRecyclerView(recyclerView);
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

    private void setLayoutManager(@NonNull RecyclerView recyclerView) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidthInDp = displayMetrics.widthPixels / displayMetrics.density;

        if (screenWidthInDp < MIN_WIDTH_IN_DP) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            return;
        }

        int snapCount = (int) (screenWidthInDp / MIN_WIDTH_IN_DP);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(snapCount, StaggeredGridLayoutManager.VERTICAL));
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

        NewsAdapter.OnItemClickListener clickListener = position ->
                startActivity(NewsDetailsActivity.getStartIntent(position, this));

        recyclerView.setAdapter(new NewsAdapter(DataUtils.NEWS, clickListener, this));

        setLayoutManager(recyclerView);
        setItemDecoration(recyclerView);
    }

}
