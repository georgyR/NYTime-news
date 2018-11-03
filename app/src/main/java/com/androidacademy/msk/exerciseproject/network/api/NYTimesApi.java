package com.androidacademy.msk.exerciseproject.network.api;

import com.androidacademy.msk.exerciseproject.network.model.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NYTimesApi {

    @GET("{section}.json")
    Single<NewsResponse> getNews(@Path("section") String section);
}
