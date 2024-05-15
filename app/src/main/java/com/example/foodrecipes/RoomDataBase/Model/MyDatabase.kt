package com.example.foodrecipes.RoomDataBase.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodrecipes.RoomDataBase.Model.Model.LoginData

@Database(entities = [LoginData::class], version = 1)
abstract class MyDatabase:RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java, "user-database"
                ).build()
            }
            return instance as MyDatabase
        }
    }
}