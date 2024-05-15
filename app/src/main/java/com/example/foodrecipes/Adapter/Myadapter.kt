package com.example.foodrecipes.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.foodrecipes.R

class Myadapter(var recipeslist: ArrayList<Recipe>, val onclick: Onclick, val fragment:String):RecyclerView.Adapter<Myadapter.Myviewholder>() {

    class Myviewholder(itemview: View,onclick: Onclick):RecyclerView.ViewHolder(itemview){
        val name:TextView = itemview.findViewById(R.id.txtname)
        val cuisine:TextView = itemview.findViewById(R.id.cuisine)
        val mealType:TextView = itemview.findViewById(R.id.mealType)
        val caloriesPerServing:TextView = itemview.findViewById(R.id.caloriesPerServing)
        val txtrating:TextView = itemview.findViewById(R.id.txtrating)
        val imageshow:ImageView = itemview.findViewById(R.id.imageshow)
        val favarite:ImageView = itemview.findViewById(R.id.favarite)
        val rootlis:ConstraintLayout = itemview.findViewById(R.id.rootlist)


        init {
            rootlis.setOnClickListener {
                onclick.rootclick(adapterPosition)
            }
            favarite.setOnClickListener {
                onclick.favarite(adapterPosition)
            }
        }


        fun buind(recipe: Recipe){
            name.text = recipe.name
            cuisine.text = recipe.cuisine
            mealType.text = recipe.mealType.get(0)
            caloriesPerServing.text ="Rs : " + recipe.caloriesPerServing.toString()
            txtrating.text = recipe.rating.toString() + "*"


            val radius = 60 // Adjust as needed

            // Apply the rounded corners transformation to the Glide request
            val requestOptions = RequestOptions().transform(RoundedCorners(radius))

            // Load the image with Glide and apply the transformation
            Glide.with(itemView)
                .load(recipe.image)
                .apply(requestOptions)
                .into(imageshow)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_recipe,parent,false)
        return Myviewholder(view,onclick)
    }

    override fun getItemCount(): Int {
       return recipeslist.size
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        holder.buind(recipeslist[position])
    }

    fun updateList(recipe: List<Recipe>) {
        recipeslist = recipe as ArrayList<Recipe>
        notifyDataSetChanged()
    }

}