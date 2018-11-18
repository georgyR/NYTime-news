package com.androidacademy.msk.exerciseproject.screen.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.di.Injector;
import com.androidacademy.msk.exerciseproject.model.AppError;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

public class AboutActivity extends MvpAppCompatActivity implements AboutView {

    private static final String DEBUG_SNACKBAR_ERROR_MESSAGE = AboutActivity.class.getSimpleName();

    @NonNull
    private View rootView;
    @NonNull
    private Button sendMessageButton;
    @NonNull
    private Button sendEmailButton;
    @NonNull
    private ImageButton telegramImageButton;
    @NonNull
    private ImageButton instagramImageButton;
    @NonNull
    private LinearLayout linearLayout;
    @NonNull
    private EditText messageEditText;

    @Inject
    @InjectPresenter
    AboutPresenter presenter;

    @ProvidePresenter
    AboutPresenter providePresenter() {
        Injector.getInstance().getAppComponent().inject(this);
        return presenter;
    }

    @NonNull
    public static Intent getStartIntent(@NonNull Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar_all);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rootView = findViewById(android.R.id.content);
        linearLayout = findViewById(R.id.linearlayout_viewprofile);

        sendMessageButton = findViewById(R.id.button_viewprofile_send_message);
        sendEmailButton = findViewById(R.id.button_viewprofile_send_email);
        telegramImageButton = findViewById(R.id.imagebutton_viewprofile_telegram);
        instagramImageButton = findViewById(R.id.imagebutton_viewprofile_instagram);
        messageEditText = findViewById(R.id.edittext_viewprofile_message);

        sendMessageButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString();
            presenter.onSendMessageButtonClicked(message);
        });

        sendEmailButton.setOnClickListener(v -> {
            String emailSubject = getString(R.string.email_subject);
            String message = messageEditText.getText().toString();
            presenter.onEmailButtonClicked(emailSubject, message);
        });

        telegramImageButton.setOnClickListener(v -> presenter.onTelegramButtonClicked());

        instagramImageButton.setOnClickListener(v -> presenter.onInstagramButtonClicked());

        addDisclaimerTextView(linearLayout);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void openApp(@NonNull Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showErrorSnackbar(AppError appError) {
        String message;
        switch (appError) {
            case SMS:
                message = getResources().getString(R.string.snackbar_no_sms_app);
                break;
            case EMAIL:
                message = getResources().getString(R.string.snackbar_no_email_app);
                break;
            case BROWSER:
                message = getResources().getString(R.string.snackbar_no_browser);
                break;
            default:
                Log.d(DEBUG_SNACKBAR_ERROR_MESSAGE, "showErrorSnackbar: Unchecked appError type");
                return;
        }
        showSnackbar(message);
    }

    private void showSnackbar(@NonNull String message) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void addDisclaimerTextView(LinearLayout linearLayout) {
        TextView disclaimerTv = new TextView(this);

        disclaimerTv.setText(R.string.disclaimer);
        disclaimerTv.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        disclaimerTv.setGravity(Gravity.CENTER);

        linearLayout.addView(disclaimerTv);
    }
}
