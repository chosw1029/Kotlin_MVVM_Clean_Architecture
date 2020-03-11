package com.nextus.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://api.github.com/"

class RemoteClient {

    fun createRetrofit(debug: Boolean): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                provideOkHttpClient(
                    provideLoggingInterceptor(
                        debug
                    )
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    // OkHttpClient 객체 생성
    private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    // 네트워크 관련 로그 출력을 위한 Interceptor
    private fun provideLoggingInterceptor(debug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (debug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }

}