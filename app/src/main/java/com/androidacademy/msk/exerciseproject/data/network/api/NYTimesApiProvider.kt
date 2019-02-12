package com.androidacademy.msk.exerciseproject.data.network.api

import com.androidacademy.msk.exerciseproject.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NYTimesApiProvider {

    private const val URL = "https://api.nytimes.com/svc/topstories/v2/"
    private const val API_KEY = "753fb43c9ecd4286b7e684e38e51d5ad"
    private const val TIMEOUT_IN_SECONDS = 5

    @JvmStatic
    fun createApi(): NYTimesApi {
        val client = buildOkHttpClient()
        val retrofit = buildRetrofit(client)
        return retrofit.create(NYTimesApi::class.java)
    }

    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun buildOkHttpClient(): OkHttpClient {

        val builder = OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor(API_KEY))

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            builder.addInterceptor(loggingInterceptor)
        }

        return builder
                .connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .build()
    }

}
