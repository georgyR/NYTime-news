package com.androidacademy.msk.exerciseproject.data.network.api;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {


    private static final String API_KEY_PARAMETER = "api-key";
    @NonNull
    private final String apiKey;

    public ApiKeyInterceptor(@NonNull String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        HttpUrl url = request.url().newBuilder()
                .addQueryParameter(API_KEY_PARAMETER, apiKey)
                .build();

        Request requestWithApiKey = request.newBuilder()
                .url(url)
                .build();

        return chain.proceed(requestWithApiKey);
    }
}
