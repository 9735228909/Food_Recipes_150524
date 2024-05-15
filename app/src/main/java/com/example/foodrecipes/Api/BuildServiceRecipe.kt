package com.example.foodrecipes.Api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BuildServiceRecipe {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/").addConverterFactory(GsonConverterFactory.create())
        .client(client).build()

    fun <T> serviceBuilderrecipe(service:Class<T>):T{
        return retrofit.create(service)
    }
}