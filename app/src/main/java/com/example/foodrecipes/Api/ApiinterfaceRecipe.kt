package com.example.foodrecipes.Api

import com.example.foodrecipes.Adapter.Recipes
import retrofit2.Call
import retrofit2.http.GET

interface ApiinterfaceRecipe {
    @GET("recipes")
    fun requestdata():Call<Recipes>
}