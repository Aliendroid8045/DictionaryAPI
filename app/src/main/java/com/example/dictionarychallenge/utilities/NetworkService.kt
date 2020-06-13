package com.example.dictionarychallenge.utilities

import com.example.dictionarychallenge.data.SearchedWordResponse
import com.google.gson.GsonBuilder


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


class NetworkService {
    /**
     * add interceptor to generate logs request and response information
     */

    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .apply {
            this.addInterceptor(interceptor)
        }.build()

    /**
     * Build retrofit client
     */
    val retrofit = Retrofit.Builder()
        .baseUrl("https://mashape-community-urban-dictionary.p.rapidapi.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    private val wordService = retrofit.create(DictionaryService::class.java)

    suspend fun getResultFromNetwork(term: CharSequence): Response<SearchedWordResponse> =
        wordService.makeCallForWordDefinition(term)
}

/**
API call to the end point, added query because we want to search word on API which is going to get append in URL
the word will be entered by user to search it
retrofit support Dispatchers.IO by default on all network call, we don't need to do specific setting to handle this call in background
 */
interface DictionaryService {
    @Headers(
        "x-rapidapi-host: mashape-community-urban-dictionary.p.rapidapi.com",
        "x-rapidapi-key: d95d32c486mshad4bda1a4b39a50p16bd90jsn72ab4dd8f06b"
    )
    @GET("define")
    suspend fun makeCallForWordDefinition(@Query("term") searchedWord: CharSequence): Response<SearchedWordResponse>
}