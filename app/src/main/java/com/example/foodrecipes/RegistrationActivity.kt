package com.example.foodrecipes

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodrecipes.RoomDataBase.Model.Model.LoginData
import com.example.foodrecipes.RoomDataBase.Model.MyDatabase
import com.example.foodrecipes.RoomDataBase.Model.UserDao
import com.example.foodrecipes.databinding.ActivityRegistrationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegistrationBinding
    private lateinit var database: MyDatabase
    lateinit var contactDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = MyDatabase.getInstance(this)
        contactDao = database.userDao()

        binding.login.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnsignup.setOnClickListener {

            val email = binding.edtemail.text.toString()
            val password = binding.edtpassword.text.toString()

            binding.progressbarsignup.visibility= View.VISIBLE

            if (validation(email,password)){
                binding.progressbarsignup.visibility= View.GONE
                val user = LoginData(email = email, password = password)
               CoroutineScope(Dispatchers.IO).launch {
                   contactDao.insert(user)
               }

                Toast.makeText(this, "User inserted successfully", Toast.LENGTH_SHORT).show()

            }

        }

    }

        private fun validation(email: String, password: String): Boolean {

            if (email.isEmpty()) {
                binding.progressbarsignup.visibility= View.GONE
                binding.edtemail.error = "Please Enter Your Email"
                return false
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.progressbarsignup.visibility= View.GONE
                Toast.makeText(this, "Please Enter a Valid Email Address", Toast.LENGTH_SHORT).show()
                return false
            }

            if (password.length < 8) {
                binding.progressbarsignup.visibility= View.GONE
                Toast.makeText(this, "Please enter a Valid Password (at least 8 characters)", Toast.LENGTH_SHORT).show()
                return false
            }

            return true

        }
}