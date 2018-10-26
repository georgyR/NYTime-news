package com.androidacademy.msk.exerciseproject.data.network;

import com.androidacademy.msk.exerciseproject.data.model.NewsRequest;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiEndpoint {

    @GET("{section}.json")
    Single<NewsRequest> getNews(@Path("section") String section);
}
