package com.androidacademy.msk.exercise2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button sendMessageBtn;
    private Button sendEmailBtn;
    private ImageButton telegramBtn;
    private ImageButton instagramBtn;
    private LinearLayout linearLayout;
    private EditText messageEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendMessageBtn = findViewById(R.id.btn_send_message);
        sendEmailBtn = findViewById(R.id.btn_send_email);
        telegramBtn = findViewById(R.id.image_btn_telegram);
        instagramBtn = findViewById(R.id.image_btn_instagram);
        messageEt = findViewById(R.id.et_message_text);
        linearLayout = findViewById(R.id.linear_layout);

        sendMessageBtn.setOnClickListener(v -> openSmsApp());
        sendEmailBtn.setOnClickListener(v -> openEmailApp());
        telegramBtn.setOnClickListener(v -> openTelegramProfile());
        instagramBtn.setOnClickListener(v -> openInstagramProfile());

        addDisclaimerTv();
    }

    private void openSmsApp() {
        String phoneNumberString = "+79165766299";
        String text = messageEt.getText().toString();

        Uri uri = Uri.parse("smsto:" + phoneNumberString);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", text);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.toast_no_sms_app, Toast.LENGTH_SHORT).show();
        }
    }

    private void openEmailApp() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        String subject = getString(R.string.email_subject);
        String text = messageEt.getText().toString();

        String addresses = "georgy.ryabykh@gmail.com";
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{addresses});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, text);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.toast_no_email_app, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean openSpecificApp(@NonNull String stringUrl, @NonNull String appPackage) {
        Uri uri = Uri.parse(stringUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(appPackage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    private void openUriInBrowser(@NonNull String stringUri) {
        Uri uri = Uri.parse(stringUri);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.toast_no_browser, Toast.LENGTH_SHORT).show();
        }
    }

    private void openInstagramProfile() {
        String instagramUrl = "https://www.instagram.com/georgy.ryabykh/";
        String instagramPackage = "com.instagram.android";

        if (!openSpecificApp(instagramUrl, instagramPackage)) {
            openUriInBrowser(instagramUrl);
        }
    }
    private void openTelegramProfile() {
        String telegramUri = "https://t.me/georgy_ryabykh/";
        String telegramPackage = "com.instagram.android";
        String telegramXpackage = "org.thunderdog.challegram";

        if (!openSpecificApp(telegramUri, telegramPackage) &&
                !openSpecificApp(telegramUri, telegramXpackage)) {

            openUriInBrowser(telegramUri);
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
}
