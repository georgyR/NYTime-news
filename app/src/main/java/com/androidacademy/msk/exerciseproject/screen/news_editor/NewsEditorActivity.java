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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem;
import com.androidacademy.msk.exerciseproject.di.Injector;
import com.androidacademy.msk.exerciseproject.screen.dialog.DatePickerDialogFragment;
import com.androidacademy.msk.exerciseproject.screen.dialog.LeaveWithoutSaveDialogFragment;
import com.androidacademy.msk.exerciseproject.screen.dialog.TimePickerDialogFragment;
import com.androidacademy.msk.exerciseproject.utils.DateUtils;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

public class NewsEditorActivity extends MvpAppCompatActivity implements
        NewsEditorView,
        LeaveWithoutSaveDialogFragment.DialogListener,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private static final String DEBUG_MENU_ITEM = NewsEditorActivity.class.getSimpleName();

    private static final String EXTRA_ID = "EXTRA_ID";

    @NonNull
    private EditText titleEditText;
    @NonNull
    private ImageView imageView;
    @NonNull
    private EditText abstractEditText;
    @NonNull
    private Button dateButton;
    @NonNull
    private Button timeButton;

    @Inject
    @InjectPresenter
    public NewsEditorPresenter presenter;

    @ProvidePresenter
    public NewsEditorPresenter providePresenter() {
        return presenter;
    }

    @NonNull
    public static Intent getStartIntent(int id, @NonNull Context context) {
        Intent intent = new Intent(context, NewsEditorActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            int id = getIntent().getIntExtra(EXTRA_ID, 0);
            Injector.getInstance(getApplicationContext()).getNewsEditorComponent(id).inject(this);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_editor);

        setTitle(R.string.news_editor_title);

        Toolbar toolbar = findViewById(R.id.toolbar_all);
        setSupportActionBar(toolbar);

        titleEditText = findViewById(R.id.edittext_newseditor_title);
        imageView = findViewById(R.id.imageview_newseditor);
        abstractEditText = findViewById(R.id.edittext_newseditor_abstract);
        dateButton = findViewById(R.id.textview_newseditor_date);
        timeButton = findViewById(R.id.textview_newseditor_time);
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
                setResult(RESULT_OK);
                finish();
                break;

            default:
                Log.d(DEBUG_MENU_ITEM, "Unknown menu item id");
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
        if (newsItem.getFullsizeImageUrl() != null) {
            Glide.with(this).load(newsItem.getFullsizeImageUrl()).into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }
        abstractEditText.setText(newsItem.getAbstractX());

        String publishedDate = newsItem.getPublishedDate();
        if (publishedDate != null) {
            String date = DateUtils.getFormattedDate(publishedDate);
            dateButton.setText(date);
            String time = DateUtils.getFormattedTime(publishedDate, this);
            timeButton.setText(time);
        }


        timeButton.setOnClickListener(v -> presenter.onTimeButtonClicked());

        dateButton.setOnClickListener(v -> presenter.onDateButtonClicked());
    }

    @Override
    public void updateTime(@NonNull String formattedTime) {
        timeButton.setText(formattedTime);
    }

    @Override
    public void updateDate(@NonNull String formattedDate) {
        dateButton.setText(formattedDate);
    }

    @Override
    public void openTimePicker(int hour, int minute) {
        TimePickerDialogFragment.newInstance(hour, minute).show(
                getSupportFragmentManager(),
                TimePickerDialogFragment.class.getSimpleName());
    }

    @Override
    public void openDatePicker(int year, int month, int day) {
        DatePickerDialogFragment.newInstance(year, month, day).show(
                getSupportFragmentManager(),
                DatePickerDialogFragment.class.getSimpleName());
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