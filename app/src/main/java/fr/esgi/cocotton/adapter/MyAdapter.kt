package fr.esgi.cocotton.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.cocotton.model.Recipe
import fr.esgi.cocotton.viewHolder.RecipeViewHolder

class MyAdapter(private val recipes: List<Recipe>) : RecyclerView.Adapter<RecipeViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        return RecipeViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int)
    {
        val recipe: Recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = recipes.size

}