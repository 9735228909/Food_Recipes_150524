package com.example.foodrecipes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodrecipes.Api.BuiledService
import com.example.foodrecipes.RoomDataBase.Model.Model.LoginData
import com.example.foodrecipes.RoomDataBase.Model.MyDatabase
import com.example.foodrecipes.RoomDataBase.Model.UserDao
import com.example.foodrecipes.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var database: MyDatabase
    lateinit var contactDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("srt", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        database = MyDatabase.getInstance(this)
        contactDao = database.userDao()

        binding.singup.setOnClickListener {
            val intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.btnLogin.setOnClickListener {
            val email = binding.edtemail.text.toString()
            val password = binding.edtpassword.text.toString()

            binding.progressbarlogin.visibility = View.VISIBLE

            if (validation(email, password)) {

                CoroutineScope(Dispatchers.IO).launch {
                    val user = contactDao.login(email, password)
                    if (user != null) {

                        editor.putInt("sta", 1)
                        editor.putString("email", email)
                        editor.putString("password", password)
                        editor.apply()

                        runOnUiThread {
                            binding.progressbarlogin.visibility = View.GONE
                            val intent =
                                Intent(this@LoginActivity, Drawer_Layout_Activity::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()
                        }
                    }
                    else {
                        runOnUiThread {
                            binding.progressbarlogin.visibility = View.GONE
                            Toast.makeText(this@LoginActivity, "Email or password incorrect", Toast.LENGTH_SHORT).show()
                        }
                }
                }
            }
        }
    }
    private fun validation(email: String, password: String): Boolean {
        if (email.isBlank() || password.isBlank()){
            if (email.isEmpty()) {
                binding.progressbarlogin.visibility= View.GONE
                binding.edtemail.error = "Please Enter Your Email"
                return false
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                binding.progressbarlogin.visibility= View.GONE
                Toast.makeText(this, "Please Enter a Valid Email Address", Toast.LENGTH_SHORT).show()
                return false
            }
            if (password.length < 8)
            {
                binding.progressbarlogin.visibility= View.GONE
                Toast.makeText(this, "Please enter a Valid Password (at least 8 characters)", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }
}