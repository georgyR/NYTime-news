package com.androidacademy.msk.exerciseproject.screen.news_editor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.androidacademy.msk.exerciseproject.App;
import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.androidacademy.msk.exerciseproject.screen.dialog.DatePickerDialogFragment;
import com.androidacademy.msk.exerciseproject.screen.dialog.LeaveWithoutSaveDialogFragment;
import com.androidacademy.msk.exerciseproject.screen.dialog.TimePickerDialogFragment;
import com.androidacademy.msk.exerciseproject.utils.DateUtils;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;

import java.util.Calendar;

public class NewsEditorActivity extends MvpAppCompatActivity implements
        NewsEditorView,
        LeaveWithoutSaveDialogFragment.DialogListener,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private static final String EXTRA_ID = "EXTRA_ID";
    public static final int RESULT_NEWS_IS_CHANGED = 40;

    @NonNull
    private EditText titleEditText;
    @NonNull
    private ImageView imageView;
    @NonNull
    private EditText abstractEditText;
    @NonNull
    private Button dateTextView;
    @NonNull
    private Button timeTextView;

    @InjectPresenter
    public NewsEditorPresenter presenter;

    @NonNull
    public static Intent getStartIntent(int id, @NonNull Context context) {
        Intent intent = new Intent(context, NewsEditorActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_editor);

        setTitle(R.string.news_editor_title);

        Toolbar toolbar = findViewById(R.id.toolbar_all);
        setSupportActionBar(toolbar);

        titleEditText = findViewById(R.id.edittext_newseditor_title);
        imageView = findViewById(R.id.imageview_newseditor);
        abstractEditText = findViewById(R.id.edittext_newseditor_abstract);
        dateTextView = findViewById(R.id.textview_newseditor_date);
        timeTextView = findViewById(R.id.textview_newseditor_time);


        int id = getIntent().getIntExtra(EXTRA_ID, 0);

        if (savedInstanceState == null) {
            presenter.onCreateActivity(id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_news_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_save_changes:
                presenter.onSaveOptionItemClicked(
                        titleEditText.getText().toString(),
                        abstractEditText.getText().toString());
                setResult(RESULT_NEWS_IS_CHANGED);
                finish();
                break;

            default:
                Log.d(App.UI_DEBUG_TAG, "Unknown menu item id");
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        DialogFragment dialog = new LeaveWithoutSaveDialogFragment();
        dialog.show(
                getSupportFragmentManager(),
                LeaveWithoutSaveDialogFragment.class.getSimpleName());
    }


    @Override
    public void showNewsDetails(DbNewsItem newsItem) {
        titleEditText.setText(newsItem.getTitle());

        Glide.with(this).load(newsItem.getFullsizeImageUrl()).into(imageView);
        abstractEditText.setText(newsItem.getAbstractX());

        String publishedDate = newsItem.getPublishedDate();
        if (publishedDate != null) {
            String date = DateUtils.getFormattedDate(publishedDate);
            dateTextView.setText(date);
            String time = DateUtils.getFormattedTime(publishedDate, this);
            timeTextView.setText(time);
        }


        timeTextView.setOnClickListener(v -> {

            Calendar calendar = DateUtils.getCalendarFromTimestamp(publishedDate);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialogFragment.newInstance(hour, minute).show(
                    getSupportFragmentManager(),
                    TimePickerDialogFragment.class.getSimpleName());
        });

        dateTextView.setOnClickListener(v -> {
            Calendar calendar = DateUtils.getCalendarFromTimestamp(publishedDate);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialogFragment.newInstance(year, month, day).show(
                    getSupportFragmentManager(),
                    DatePickerDialogFragment.class.getSimpleName());
        });
    }

    @Override
    public void updateTime(@NonNull String formattedTime) {
        timeTextView.setText(formattedTime);
    }

    @Override
    public void updateDate(@NonNull String formattedDate) {
        dateTextView.setText(formattedDate);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        presenter.onSetTime(hourOfDay, minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        presenter.onSetDate(year, month, dayOfMonth);
    }

    @Override
    public void onDialogPositiveClick() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onDialogNegativeClick() {
    }
}
