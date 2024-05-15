package com.example.foodrecipes.Fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentDetailesBinding
import com.example.foodrecipes.databinding.FragmentHomeBinding
import com.razorpay.Checkout
import org.json.JSONObject

class DetailesFragment : Fragment() {
    private  var _binding : FragmentDetailesBinding?= null
    private val binding get() = _binding!!
    var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = arguments
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner = binding.spinner
        val spinerlist = arrayOf(1,2,3,4,5,6,7,8,9)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinerlist)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.adapter = spinnerAdapter


        val bundle = arguments
        val name = bundle?.getString("name") ?: ""
        val ingredients = bundle?.getStringArray("ingredients")?.joinToString(", ") ?: ""
        val instructions = bundle?.getStringArray("instructions")?.joinToString("\n") ?: ""
        val cuisine = bundle?.getString("cuisine") ?: ""
        val tags = bundle?.getStringArray("tags")?.joinToString(", ") ?: ""
        val mealType = bundle?.getStringArray("mealtype")?.joinToString(", ") ?: ""
        val caloriesPerServing = bundle?.getInt("caloriesPerServing", 0).toString().toInt()
        val image = bundle?.getString("image")

        // Update UI with the retrieved data
        binding.txtname.text = name

//        binding.ingredients.text = ingredients
        binding.ingredients.text = truncateText(ingredients, 6)  // Show only the first word initially
        binding.viewmore.setOnClickListener {
            if (binding.ingredients.text == ingredients) {
                // If the full text is already displayed, show only the first word
                binding.ingredients.text = truncateText(ingredients, 6)
                binding.viewmore.text = "View More"
            } else {
                // If only the first word is displayed, show the full text
                binding.ingredients.text = ingredients
                binding.viewmore.text = "View Less"
            }
        }


        binding.instructions.text = truncateText(instructions, 6)  // Show only the first word initially
        binding.viewmore2.setOnClickListener {
            if (binding.instructions.text == instructions) {
                // If the full text is already displayed, show only the first word
                binding.instructions.text = truncateText(instructions, 6)
                binding.viewmore2.text = "View More"
            } else {
                // If only the first word is displayed, show the full text
                binding.instructions.text = instructions
                binding.viewmore2.text = "View Less"
            }
        }
        binding.cuisine.text = cuisine
        binding.tags.text = tags
        binding.mealType.text = mealType
        binding.caloriesPerServing.text = "Rs : $caloriesPerServing"
        Glide.with(requireContext()).load(image).into(binding.imageview)

        binding.btnbuy.setOnClickListener{
            val selectedSpinnerValue = spinner.selectedItem.toString().toInt()
            val totalCalories = caloriesPerServing * selectedSpinnerValue
            val amount = totalCalories * 100

            Checkout.preload(requireActivity())
            val checkout = Checkout()
            checkout.setKeyID("rzp_test_gIpeUGLhCSG5GI")

//            val quantity = selectedItem.toInt()
//            val totalCalories = recipe.caloriesPerServing * quantity
//            val amount = Math.round(totalCalories.toFloat() * 100)
//            Toast.makeText(itemView.context, "Total Calories: $totalCalories", Toast.LENGTH_SHORT).show()


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
                co.open(context as Activity, options)
            } catch (e: Exception) {
                Toast.makeText(context, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }

    }

    private fun truncateText(text: String, wordCount: Int): String {
        val words = text.split(" ").take(wordCount)
        return words.joinToString(" ")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}