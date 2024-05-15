package com.example.foodrecipes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val sharedPreferences = getSharedPreferences("srt", Context.MODE_PRIVATE)
       val editor = sharedPreferences.getInt("sta",0)

        Handler(Looper.getMainLooper()).postDelayed({

            if (editor==1){
                val intent = Intent(this,Drawer_Layout_Activity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this,FlipperActivity::class.java)
                startActivity(intent)
                finish()
            }

        },2000)

    }
}