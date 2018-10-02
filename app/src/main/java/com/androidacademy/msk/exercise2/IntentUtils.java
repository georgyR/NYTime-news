package com.androidacademy.msk.exercise2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

public class IntentUtils {

    @NonNull
    public static Intent getEmailIntent(@NonNull String email,
                                        @NonNull String subject,
                                        @NonNull String message) {
        return new Intent(Intent.ACTION_SENDTO)
                .putExtra(Intent.EXTRA_EMAIL, new String[]{email})
                .putExtra(Intent.EXTRA_SUBJECT, subject)
                .putExtra(Intent.EXTRA_TEXT, message)
                .setData(Uri.parse("mailto:"));
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
