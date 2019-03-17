package com.androidacademy.msk.exerciseproject.di.module

import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApi
import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApiProvider
import javax.inject.Inject
import javax.inject.Provider


class NetworkProvider @Inject constructor(): Provider<NYTimesApi> {
    override fun get(): NYTimesApi = NYTimesApiProvider.createApi()
}