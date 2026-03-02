package com.example.common.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitFactory {

    fun create(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(HttpConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

