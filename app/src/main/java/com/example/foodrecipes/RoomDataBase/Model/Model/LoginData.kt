package com.example.foodrecipes.RoomDataBase.Model.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class LoginData(
    @PrimaryKey
    val id:Int = 0,
    val email:String,
    val password:String)

//data class Response(val error: String)
