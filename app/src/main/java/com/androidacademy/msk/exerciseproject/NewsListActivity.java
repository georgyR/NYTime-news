package com.androidacademy.msk.exerciseproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;

import com.androidacademy.msk.exerciseproject.data.DataUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NewsAdapter(DataUtils.generateNews()));

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Surface.ROTATION_90 || orientation == Surface.ROTATION_270) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
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
                startActivity(AboutActivity.getIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
