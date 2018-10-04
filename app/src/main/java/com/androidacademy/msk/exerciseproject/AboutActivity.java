package com.androidacademy.msk.exerciseproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidacademy.msk.exerciseproject.data.SocialNetworkApp;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private static final String EMAIL = "georgy.ryabykh@gmail.com";
    private static final String PHONE_NUMBER = "+79165766299";

    private View rootView;
    private Button sendMessageBtn;
    private Button sendEmailBtn;
    private ImageButton telegramBtn;
    private ImageButton instagramBtn;
    private LinearLayout linearLayout;
    private EditText messageEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        rootView = findViewById(R.id.root_view);
        linearLayout = findViewById(R.id.linear_layout);

        sendMessageBtn = findViewById(R.id.btn_send_message);
        sendEmailBtn = findViewById(R.id.btn_send_email);
        telegramBtn = findViewById(R.id.image_btn_telegram);
        instagramBtn = findViewById(R.id.image_btn_instagram);
        messageEt = findViewById(R.id.et_message_text);

        sendMessageBtn.setOnClickListener(v -> {
            String message = messageEt.getText().toString();
            openSmsApp(PHONE_NUMBER, message);
        });

        sendEmailBtn.setOnClickListener(v -> {
            String emailSubject = getString(R.string.email_subject);
            String message = messageEt.getText().toString();
            openEmailApp(EMAIL, emailSubject, message);
        });

        telegramBtn.setOnClickListener(v -> {
            if (!openSpecificApp(SocialNetworkApp.TELEGRAM) &&
                    !openSpecificApp(SocialNetworkApp.TELEGRAM_X)) {
                openUriInBrowser(SocialNetworkApp.TELEGRAM.getAccountUrl());
            }
        });
        instagramBtn.setOnClickListener(v -> {
            if (!openSpecificApp(SocialNetworkApp.INSTAGRAM)) {
                openUriInBrowser(SocialNetworkApp.INSTAGRAM.getAccountUrl());
            }
        });

        addDisclaimerTv();
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

    private void addDisclaimerTv() {
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

    @NonNull
    public static Intent getIntent(@NonNull Context context) {
        return new Intent(context, AboutActivity.class);
    }
}
