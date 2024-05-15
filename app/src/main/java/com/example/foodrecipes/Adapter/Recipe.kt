package com.example.foodrecipes.Adapter

data class Recipe(
    val caloriesPerServing:Int,
    val cuisine: String,
    val image: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val mealType: List<String>,
    val name: String,
    val rating: Double,
    val tags: List<String>,
    var statues:Boolean
)