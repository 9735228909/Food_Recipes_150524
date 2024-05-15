package com.example.foodrecipes.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.foodrecipes.OnclickAdd_to_cart
import com.example.foodrecipes.R
import com.razorpay.Checkout
import org.json.JSONObject
import kotlin.time.times

class MyadapterAddtocart(val recipesaddtolist: ArrayList<Recipe>, val onclick: OnclickAdd_to_cart,val context: Context) :
    RecyclerView.Adapter<MyadapterAddtocart.Myviewholder>() {

    class Myviewholder(itemView: View, onclick: OnclickAdd_to_cart,context: Context) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.txtxname)
        private val cuisine: TextView = itemView.findViewById(R.id.txtcuisine)
        private val mealType: TextView = itemView.findViewById(R.id.txtmealType)
        private val caloriesPerServing: TextView = itemView.findViewById(R.id.txtcaloriesPerServing)
        private val spinnerlist: Spinner = itemView.findViewById(R.id.spinnerlist)
        private val txttags: TextView = itemView.findViewById(R.id.txttags)
        private val imageshow: ImageView = itemView.findViewById(R.id.image_add)
        private val delete: TextView = itemView.findViewById(R.id.txtdlete)
        private val payment: TextView = itemView.findViewById(R.id.txtbuy)

        private var selectedItem: String = ""

        init {
            delete.setOnClickListener {
                onclick.delete(adapterPosition)
            }
            payment.setOnClickListener {
                onclick.payment(adapterPosition)
            }

            val quantityArray = arrayOf("1", "2", "3", "4", "5") // You can adjust this array as needed

            val adapter = ArrayAdapter(itemView.context, android.R.layout.simple_spinner_item, quantityArray)

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerlist.adapter = adapter

            spinnerlist.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // Store the selected item
                    selectedItem = parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle nothing selected if needed
                }
            }
        }


        fun bind(recipe: Recipe) {
            name.text = recipe.name
            cuisine.text = recipe.cuisine
            mealType.text = recipe.mealType.firstOrNull() ?: ""
            caloriesPerServing.text = "Rs : ${recipe.caloriesPerServing}"
            txttags.text = recipe.tags.firstOrNull() ?: ""

            val radius = 60 // Adjust as needed

            val requestOptions = RequestOptions().transform(RoundedCorners(radius))

            // Load the image with Glide and apply the transformation
            Glide.with(itemView)
                .load(recipe.image)
                .apply(requestOptions)
                .into(imageshow)

            payment.setOnClickListener {
                val quantity = selectedItem.toInt()
                val totalCalories = recipe.caloriesPerServing * quantity
                val amount = Math.round(totalCalories.toFloat() * 100)
                Toast.makeText(itemView.context, "Total Calories: $totalCalories", Toast.LENGTH_SHORT).show()


                val co = Checkout()

                try {
                    val options = JSONObject()
                    options.put("Abhishek","Pramanik")
                    options.put("description","lkcjalkscnals")
                    options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
                    options.put("theme.color", "#3399cc")
                    options.put("currency","INR");
                    options.put("amount",amount)//pass amount in currency subunits

                    val retryObj =  JSONObject();
                    retryObj.put("enabled", true);
                    retryObj.put("max_count", 2);
                    options.put("retry", retryObj);

                    val prefill = JSONObject()
                    prefill.put("email","abhishek@gmail.com")
                    prefill.put("contact","9735228909")

                    options.put("prefill",prefill)
                    co.open(itemView.context as Activity, options)
                } catch (e: Exception) {
                    Toast.makeText(itemView.context, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_to_cart_list, parent, false)
        return Myviewholder(view, onclick,context)
    }

    override fun getItemCount(): Int {
        return recipesaddtolist.size
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        val recipe = recipesaddtolist[position]
        holder.bind(recipe)
    }
}
