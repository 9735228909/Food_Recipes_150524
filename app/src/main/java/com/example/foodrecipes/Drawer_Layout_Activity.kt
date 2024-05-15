package com.example.foodrecipes

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.foodrecipes.Fragment.Add_To_Cart_Fragment
import com.example.foodrecipes.Fragment.DetailesFragment
import com.example.foodrecipes.Fragment.HomeFragment
import com.example.foodrecipes.databinding.ActivityDrawerLayoutBinding
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener

class Drawer_Layout_Activity : AppCompatActivity(),PaymentResultWithDataListener {
    private lateinit var binding:ActivityDrawerLayoutBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawerLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("srt", Context.MODE_PRIVATE)
       editor = sharedPreferences.edit()

        drawerLayout = binding.main
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navigationview = binding.navigationdrwwer
        navigationview.setNavigationItemSelectedListener {
            it.isChecked = true
            when(it.itemId){
                R.id.home->{
                    replace(HomeFragment(),it.title.toString())
                    Toast.makeText(this,"Home Fragment",Toast.LENGTH_LONG).show()
                }
                R.id.add_to_cart->{
                    replace(Add_To_Cart_Fragment(),it.title.toString())
                    Toast.makeText(this,"Add_to_cart_fragment Fragment",Toast.LENGTH_LONG).show()
                }
            }
            true
        }

        val navigation = navigationview.getHeaderView(0)
        val emailtxt = navigation.findViewById<TextView>(R.id.txtemail)
        val email = intent.getStringExtra("email")
        emailtxt.text = email

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu,menu)
        return true
    }

    private fun replace(fragment:Fragment,title:String) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,fragment).commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        when (item.itemId) {
            R.id.logout -> {
                editor.putInt("sta",0)
                editor.clear()
                editor.apply()

                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()

                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_box)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams
        val textname = dialog.findViewById<TextView>(R.id.txtbody)
        textname.text = "Thank you \n Payment done successfully"
        textname.setTextColor(ContextCompat.getColor(this, R.color.green))
        dialog.show()
        val btnyes = dialog.findViewById<Button>(R.id.btnyes)
        val btnno = dialog.findViewById<Button>(R.id.btnno)

        btnyes.setOnClickListener {
            // Replace with HomeFragment
            replace(HomeFragment(), "Home")
            dialog.dismiss() // Dismiss the dialog after navigation
        }

        btnno.setOnClickListener {
            dialog.dismiss() // Dismiss the dialog if "No" button is clicked
        }

        Toast.makeText(this, "Payment done", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_box)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams
        val textname = dialog.findViewById<TextView>(R.id.txtbody)
        textname.text = "Payment Failed \n try again"
        textname.setTextColor(ContextCompat.getColor(this, R.color.read))
        dialog.show()

        val btnyes = dialog.findViewById<Button>(R.id.btnyes)
        val btnno = dialog.findViewById<Button>(R.id.btnno)

        btnyes.setOnClickListener {
            dialog.dismiss() // Dismiss the dialog after navigation
        }

        btnno.setOnClickListener {
            dialog.dismiss() // Dismiss the dialog if "No" button is clicked
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        when (fragment) {
            is Add_To_Cart_Fragment, is DetailesFragment -> {
                replace(HomeFragment(), "Home")
            }
            else -> super.onBackPressed()
        }
    }
}