package com.androidacademy.msk.exerciseproject.screen.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.utils.IntentUtils;
import com.androidacademy.msk.exerciseproject.data.SocialNetworkApp;


public class AboutActivity extends AppCompatActivity {

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

    @NonNull
    public static Intent getStartIntent(@NonNull Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.all_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rootView = findViewById(android.R.id.content);
        linearLayout = findViewById(R.id.profile_information__linear_layout);

        sendMessageButton = findViewById(R.id.profile_information__button_send_message);
        sendEmailButton = findViewById(R.id.profile_information__button_send_email);
        telegramImageButton = findViewById(R.id.profile_information__imagebutton_telegram);
        instagramImageButton = findViewById(R.id.profile_information__imagebutton_instagram);
        messageEditText = findViewById(R.id.profile_information__edittext_message_text);

        sendMessageButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString();
            openSmsApp(PHONE_NUMBER, message);
        });

        sendEmailButton.setOnClickListener(v -> {
            String emailSubject = getString(R.string.email_subject);
            String message = messageEditText.getText().toString();
            openEmailApp(EMAIL, emailSubject, message);
        });

        telegramImageButton.setOnClickListener(v -> {
            if (!openSpecificApp(SocialNetworkApp.TELEGRAM) &&
                    !openSpecificApp(SocialNetworkApp.TELEGRAM_X)) {
                openUriInBrowser(SocialNetworkApp.TELEGRAM.getAccountUrl());
            }
        });
        instagramImageButton.setOnClickListener(v -> {
            if (!openSpecificApp(SocialNetworkApp.INSTAGRAM)) {
                openUriInBrowser(SocialNetworkApp.INSTAGRAM.getAccountUrl());
            }
        });

        addDisclaimerTextView(linearLayout);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void openSmsApp(@NonNull String phoneNumber, @NonNull String message) {
        Intent intent = IntentUtils.getSmsAppIntent(phoneNumber, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            showSnackbar(getResources().getString(R.string.snackbar_no_sms_app));
        }
    }

    private void openEmailApp(@NonNull String email,
                              @NonNull String subject,
                              @NonNull String message) {
        Intent intent = IntentUtils.getEmailIntent(email, subject, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            showSnackbar(getResources().getString(R.string.snackbar_no_email_app));
        }
    }

    private boolean openSpecificApp(@NonNull SocialNetworkApp app) {
        Intent intent = IntentUtils.getSpecificIntent(app);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    private void openUriInBrowser(@NonNull String stringUri) {
        Intent intent = IntentUtils.getBrowserIntent(stringUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            showSnackbar(getResources().getString(R.string.snackbar_no_browser));
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

    private void showSnackbar(@NonNull String message) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
