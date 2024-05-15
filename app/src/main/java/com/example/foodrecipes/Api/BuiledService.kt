package com.example.foodrecipes.Api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BuiledService {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://swaruphd.site/api/").addConverterFactory(GsonConverterFactory.create()).client(client).build()

    fun <T> serviceBuilder(service :Class<T>):T{
        return retrofit.create(service)
    }
}