package com.example.foodrecipes.Fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.foodrecipes.Adapter.Myadapter
import com.example.foodrecipes.Adapter.Onclick
import com.example.foodrecipes.Adapter.Recipe
import com.example.foodrecipes.Adapter.Recipes
import com.example.foodrecipes.Api.ApiinterfaceRecipe
import com.example.foodrecipes.Api.BuildServiceRecipe
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Response

var addtocartlist = ArrayList<Recipe>()

class HomeFragment : Fragment(), Onclick {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: Myadapter
    private var recipelist = ArrayList<Recipe>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                filterRecipes(query)
                return true
            }

        })

        binding.progressbar.visibility = View.VISIBLE
        adapter = Myadapter(recipelist, this,"HomeFragment")
        binding.recyclerviewhome.adapter = adapter

        val retrofit = BuildServiceRecipe.serviceBuilderrecipe(ApiinterfaceRecipe::class.java)
        retrofit.requestdata().enqueue(object : retrofit2.Callback<Recipes> {
            override fun onResponse(call: Call<Recipes>, response: Response<Recipes>) {
                binding.progressbar.visibility = View.GONE
                if (response.isSuccessful) {
                    val data = response.body()?.recipes
                    recipelist.addAll(data!!)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Wrong", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(p0: Call<Recipes>, t: Throwable) {
                binding.progressbar.visibility = View.GONE
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun filterRecipes(query: String?) {
        if (query.isNullOrEmpty()) {
            adapter.updateList(recipelist)
        } else {
            val filteredList = recipelist.filter { recipe ->
                recipe.name.contains(query, ignoreCase = true)
            }
            adapter.updateList(filteredList)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun rootclick(position: Int) {
        val currentindex = recipelist[position]

        val bundle = Bundle()
        val fragment = DetailesFragment()
        fragment.arguments = bundle

        bundle.putString("name", currentindex.name)
        bundle.putStringArray("ingredients", currentindex.ingredients.toTypedArray())
        bundle.putStringArray("instructions", currentindex.instructions.toTypedArray())
        bundle.putString("cuisine", currentindex.cuisine)
        bundle.putStringArray("tags", currentindex.tags.toTypedArray())
        bundle.putStringArray("mealtype", currentindex.mealType.toTypedArray())
        bundle.putInt("caloriesPerServing", currentindex.caloriesPerServing)
        bundle.putString("image", currentindex.image)

        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()

    }

    override fun favarite(position: Int) {

        val currentindex = recipelist[position]

        if (addtocartlist.contains(currentindex)) {
            Toast.makeText(requireContext(), "Item already added to cart", Toast.LENGTH_SHORT).show()
        } else {
            addtocartlist.add(currentindex)
            adapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Item  added to cart", Toast.LENGTH_SHORT).show()
        }

    }
}
