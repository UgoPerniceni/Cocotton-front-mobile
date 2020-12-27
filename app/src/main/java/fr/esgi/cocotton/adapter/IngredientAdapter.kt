package fr.esgi.cocotton.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.cocotton.model.Ingredient
import fr.esgi.cocotton.viewHolder.IngredientViewHolder

class IngredientAdapter(private val ingredients: List<Ingredient>) : RecyclerView.Adapter<IngredientViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        return IngredientViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int)
    {
        val ingredient: Ingredient = ingredients[position]
        holder.bind(ingredient)
    }

    override fun getItemCount(): Int = ingredients.size
}