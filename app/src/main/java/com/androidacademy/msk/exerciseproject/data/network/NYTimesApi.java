package com.androidacademy.msk.exerciseproject.data.network;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NYTimesApi {

    private static final String URL = "https://api.nytimes.com/svc/topstories/v2/";
    private static final String API_KEY = "753fb43c9ecd4286b7e684e38e51d5ad";
    private static final int TIMEOUT_IN_SECONDS = 2;

    private NYTimesApi() {
        throw new RuntimeException("There is must be no instance!");
    }

    @NonNull
    private static Retrofit buildRetrofit(@NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @NonNull
    private static OkHttpClient buildOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .addInterceptor(new ApiKeyInterceptor(API_KEY))
                .addInterceptor(loggingInterceptor)
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    @NonNull
    public static ApiEndpoint createApiEndpoint() {
        OkHttpClient client = buildOkHttpClient();
        Retrofit retrofit = buildRetrofit(client);
        return retrofit.create(ApiEndpoint.class);
    }


}
