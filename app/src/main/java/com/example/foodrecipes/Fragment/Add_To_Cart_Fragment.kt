package com.example.foodrecipes.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.foodrecipes.Adapter.MyadapterAddtocart
import com.example.foodrecipes.Adapter.Recipe
import com.example.foodrecipes.OnclickAdd_to_cart
import com.example.foodrecipes.databinding.FragmentAddToCartBinding

class Add_To_Cart_Fragment : Fragment(),OnclickAdd_to_cart {
    private var _binding: FragmentAddToCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var addtocartadapter : MyadapterAddtocart
    private var addtocart = ArrayList<Recipe>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddToCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (item in addtocartlist){
                addtocart.add(item)
        }

        addtocartadapter = MyadapterAddtocart(addtocart,this,requireContext())
        binding.addtocartrecyclerview.adapter = addtocartadapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun delete(position: Int) {
       val currentindex = addtocart[position]
        Toast.makeText(requireContext(), "item Delete Successfully", Toast.LENGTH_SHORT).show()
        addtocartlist.remove(currentindex)
        addtocart.removeAt(position)
        addtocartadapter.notifyDataSetChanged()
    }

    override fun payment(position: Int) {
        val currentindex = addtocart[position]

        Toast.makeText(requireContext(), "item Delete Successfully", Toast.LENGTH_SHORT).show()

//        addtocartlist.remove(currentindex)
//        addtocart.removeAt(position)

//        addtocartadapter.notifyDataSetChanged()

    }
}
