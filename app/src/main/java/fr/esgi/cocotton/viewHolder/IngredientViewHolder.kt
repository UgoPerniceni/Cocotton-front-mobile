package fr.esgi.cocotton.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.cocotton.R
import fr.esgi.cocotton.model.Ingredient

class IngredientViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(
                inflater.inflate(R.layout.ingredient_item, parent, false)
        )
{

    private var name: TextView? = null

    init
    {
        name = itemView.findViewById(R.id.ingredient_item_name)
    }

    fun bind(ingredient: Ingredient)
    {
        name?.text = ingredient.name
    }
}