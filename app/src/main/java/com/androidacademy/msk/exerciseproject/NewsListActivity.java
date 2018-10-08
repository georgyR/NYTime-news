package com.androidacademy.msk.exerciseproject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.androidacademy.msk.exerciseproject.data.DataUtils;

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

    private static final int BIG_WIDTH_IN_DP = 600;
    private static final int SPAN_COUNT = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        NewsAdapter.OnItemClickListener clickListener = position ->
                startActivity(NewsDetailsActivity.getStartIntent(position, this));

        recyclerView.setAdapter(new NewsAdapter(DataUtils.NEWS, clickListener));

        setLayoutManager(recyclerView);
        setItemDecoration(recyclerView);
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

        if (screenWidthInDp < BIG_WIDTH_IN_DP) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(
                    new StaggeredGridLayoutManager(SPAN_COUNT,
                            StaggeredGridLayoutManager.VERTICAL));
        }
    }

    private void setItemDecoration(@NonNull RecyclerView recyclerView) {
        DividerItemDecoration decoration
                = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        decoration.setDrawable(dividerDrawable);
        recyclerView.addItemDecoration(decoration);
    }

}
