package com.androidacademy.msk.exerciseproject.data.network.api

import com.androidacademy.msk.exerciseproject.data.network.model.NewsResponse

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface NYTimesApi {

    @GET("{section}.json")
    fun getNews(@Path("section") section: String): Single<NewsResponse>
}
