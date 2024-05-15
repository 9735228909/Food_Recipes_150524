package com.example.foodrecipes

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodrecipes.databinding.ActivityFlipperBinding

class FlipperActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlipperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlipperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.skip.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}