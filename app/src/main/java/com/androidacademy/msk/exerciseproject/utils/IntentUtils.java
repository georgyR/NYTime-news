package com.androidacademy.msk.exerciseproject.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.androidacademy.msk.exerciseproject.data.network.SocialNetworkApp;

public class IntentUtils {

    private final Context appContext;

    public IntentUtils(Context appContext) {
       this.appContext = appContext;
    }

    @Nullable
    public Intent getEmailIntent(@NonNull String email,
                                        @NonNull String subject,
                                        @NonNull String message) {

        String mailto = "mailto:" + email +
                "?cc=" +
                "&subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(message);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));

        return getIntentForExistingApp(emailIntent);
    }

    @Nullable
    public Intent getSpecificIntent(@NonNull SocialNetworkApp app) {
        Uri uri = Uri.parse(app.getAccountUrl());
        Intent specificIntent = new Intent(Intent.ACTION_VIEW, uri).setPackage(app.getAppPackage());
        return getIntentForExistingApp(specificIntent);
    }

    @Nullable
    public Intent getBrowserIntent(@NonNull String stringUri) {
        Uri uri = Uri.parse(stringUri);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);

        return getIntentForExistingApp(browserIntent);
    }

    @Nullable
    public Intent getSmsAppIntent(@NonNull String phoneNumber, @NonNull String message) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri).putExtra("sms_body", message);

        return getIntentForExistingApp(smsIntent);
    }

    @Nullable
    private Intent getIntentForExistingApp(@NonNull Intent intent) {
        if (intent.resolveActivity(appContext.getPackageManager()) == null) {
            return null;
        } else {
            return intent;
        }
    }





}
