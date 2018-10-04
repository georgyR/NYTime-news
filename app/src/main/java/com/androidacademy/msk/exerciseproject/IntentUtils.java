package com.androidacademy.msk.exerciseproject;

import android.content.Intent;
import android.net.Uri;

import com.androidacademy.msk.exerciseproject.data.SocialNetworkApp;

import androidx.annotation.NonNull;

public class IntentUtils {

    @NonNull
    public static Intent getEmailIntent(@NonNull String email,
                                        @NonNull String subject,
                                        @NonNull String message) {

        String mailto = "mailto:" + email +
                "?cc=" +
                "&subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(message);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));
        return  emailIntent;
    }

    @NonNull
    public static Intent getSpecificIntent(@NonNull SocialNetworkApp app) {

        Uri uri = Uri.parse(app.getAccountUrl());
        return new Intent(Intent.ACTION_VIEW, uri)
                .setPackage(app.getAppPackage());
    }

    @NonNull
    public static Intent getBrowserIntent(@NonNull String stringUri) {
        Uri uri = Uri.parse(stringUri);
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    @NonNull
    public static Intent getSmsAppIntent(@NonNull String phoneNumber, @NonNull String message) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        return new Intent(Intent.ACTION_SENDTO, uri)
                .putExtra("sms_body", message);
    }


}
