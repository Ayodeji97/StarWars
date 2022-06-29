package com.financials.starwars.business.datasource.utils

import com.financials.starwars.business.datasource.remote.StarWarsService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val okHttpClient: OkHttpClient
    get() = OkHttpClient.Builder().build()

internal fun makeTestApiService(mockWebServer: MockWebServer): StarWarsService = Retrofit.Builder()
    .baseUrl(mockWebServer.url("/"))
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    .build()
    .create(StarWarsService::class.java)