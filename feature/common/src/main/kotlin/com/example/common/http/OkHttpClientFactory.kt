package com.example.common.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

internal object OkHttpClientFactory {

    fun create(isDebug: Boolean): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (isDebug) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(HttpConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(HttpConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(HttpConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }
}

