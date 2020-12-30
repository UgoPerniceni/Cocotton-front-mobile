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

    private var number: TextView? = null
    private var name: TextView? = null

    init
    {
        number = itemView.findViewById(R.id.ingredient_item_number)
        name = itemView.findViewById(R.id.ingredient_item_name)
    }

    fun bind(ingredient: Ingredient, position: Int)
    {
        number?.text = position.toString()
        name?.text = ingredient.name
    }
}