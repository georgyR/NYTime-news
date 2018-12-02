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
import com.androidacademy.msk.exerciseproject.data.network.SocialNetworkApp;
import com.androidacademy.msk.exerciseproject.di.Injector;
import com.androidacademy.msk.exerciseproject.model.AppError;
import com.androidacademy.msk.exerciseproject.screen.newslist.NewsAdapter;
import com.androidacademy.msk.exerciseproject.utils.IntentUtils;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

public class AboutActivity extends MvpAppCompatActivity implements AboutView {

    private static final String DEBUG_SNACKBAR_ERROR_MESSAGE = AboutActivity.class.getSimpleName();

    private static final String EMAIL = "georgy.ryabykh@gmail.com";
    private static final String PHONE_NUMBER = "+79165766299";

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
    IntentUtils intentUtils;

    @InjectPresenter
    AboutPresenter presenter;

    @NonNull
    public static Intent getStartIntent(@NonNull Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Injector.getInstance().getAppComponent().inject(this);

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

        sendMessageButton.setOnClickListener(v -> presenter.onSendMessageButtonClicked());

        sendEmailButton.setOnClickListener(v -> presenter.onEmailButtonClicked());

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
    public void openSmsApp() {
        String message = messageEditText.getText().toString();
        Intent smsAppIntent = intentUtils.getSmsAppIntent(PHONE_NUMBER, message);
        if (!openApp(smsAppIntent)) {
            showErrorSnackbar(AppError.SMS);
        }
    }

    @Override
    public void openEmailApp() {
        String emailSubject = getString(R.string.email_subject);
        String message = messageEditText.getText().toString();
        Intent emailIntent = intentUtils.getEmailIntent(EMAIL, emailSubject, message);
        if (!openApp(emailIntent)) {
            showErrorSnackbar(AppError.EMAIL);
        }
    }

    @Override
    public void openTelegramApp() {
        Intent telegramIntent = intentUtils.getSpecificIntent(SocialNetworkApp.TELEGRAM);
        Intent telegramxIntent = intentUtils.getSpecificIntent(SocialNetworkApp.TELEGRAM_X);
        if (openApp(telegramIntent) || openApp(telegramxIntent)) {
            return;
        }
        Intent browserIntent = intentUtils.getBrowserIntent(SocialNetworkApp.TELEGRAM.getAccountUrl());
        if (!openApp(browserIntent)) {
            showErrorSnackbar(AppError.BROWSER);
        }
    }

    @Override
    public void openIstagramApp() {
        Intent instagramIntent = intentUtils.getSpecificIntent(SocialNetworkApp.INSTAGRAM);
        if (openApp(instagramIntent)) {
            return;
        }
        Intent browserIntent = intentUtils.getBrowserIntent(SocialNetworkApp.INSTAGRAM.getAccountUrl());
        if (!openApp(browserIntent)) {
            showErrorSnackbar(AppError.BROWSER);
        }
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

    private void showErrorSnackbar(@NonNull AppError appError) {
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

    private boolean openApp(@Nullable Intent intent) {
        if (intent != null) {
            startActivity(intent);
            return true;
        }
        return false;
    }
}
