package com.example.beersearchapp.di

import com.example.beersearchapp.data.api.BeerApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    single { httpClient() }
    single { createRetrofit(get(), "https://api.punkapi.com/v2/") }
    single { createApi<BeerApi> (get())}
    //single { makeApi<BeerApi>(get(),"https://api.punkapi.com/v2/beers") }
}

private fun httpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .readTimeout(25, TimeUnit.SECONDS)
        .writeTimeout(25, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .addInterceptor(loggingInterceptor)
        .build()
}

private fun createRetrofit(
    okHttpClient: OkHttpClient,
    url: String
): Retrofit {
    val gson = GsonBuilder()
        .setLenient()
        .create()

    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()
}

private inline fun <reified T> makeApi(
    okHttpClient: OkHttpClient, url: String
): T {
    val gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}
private inline fun <reified T> createApi(
    retrofit: Retrofit
): T {
    return retrofit.create(T::class.java)
}