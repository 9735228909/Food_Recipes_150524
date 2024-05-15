package com.example.foodrecipes.RoomDataBase.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodrecipes.RoomDataBase.Model.Model.LoginData

@Dao
interface UserDao {
    @Insert
   suspend fun insert(loginData: LoginData)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
   suspend fun login(email: String, password: String): LoginData?
}