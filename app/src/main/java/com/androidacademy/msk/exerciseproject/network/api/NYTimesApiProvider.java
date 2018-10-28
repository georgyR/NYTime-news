package com.androidacademy.msk.exerciseproject.network.api;

import android.os.Build;
import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NYTimesApiProvider {

    private static final String URL = "https://api.nytimes.com/svc/topstories/v2/";
    private static final String API_KEY = "753fb43c9ecd4286b7e684e38e51d5ad";
    private static final int TIMEOUT_IN_SECONDS = 5;

    private NYTimesApiProvider() {
        throw new UnsupportedOperationException("There should be no class instance");
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
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor =
                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

            builder.addInterceptor(loggingInterceptor);
        }

        return builder
                .addInterceptor(new ApiKeyInterceptor(API_KEY))
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    @NonNull
    public static NYTimesApi createApi() {
        OkHttpClient client = buildOkHttpClient();
        Retrofit retrofit = buildRetrofit(client);
        return retrofit.create(NYTimesApi.class);
    }


}
