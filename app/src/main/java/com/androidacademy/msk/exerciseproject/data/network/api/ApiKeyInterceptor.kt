package com.androidacademy.msk.exerciseproject.data.network.api


import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {

    companion object {
        private const val API_KEY_PARAMETER = "api-key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url().newBuilder()
                .addQueryParameter(API_KEY_PARAMETER, apiKey)
                .build()

        val requestWithApiKey = request.newBuilder()
                .url(url)
                .build()

        return chain.proceed(requestWithApiKey)
    }

}
