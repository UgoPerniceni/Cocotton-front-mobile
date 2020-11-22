package fr.esgi.cocotton.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.cocotton.R
import fr.esgi.cocotton.model.Recipe

class RecipeViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(
        inflater.inflate(R.layout.recipe_item, parent, false)
    )
{

    private var name: TextView? = null
    private var time: TextView? = null
    private var image: TextView? = null

    init
    {
        name = itemView.findViewById(R.id.name)
        time = itemView.findViewById(R.id.time)
        image = itemView.findViewById(R.id.image)
    }

    fun bind(recipe: Recipe)
    {
        // name?.text = "${user.firstName} ${user.lastName}"
        name?.text = recipe.name
        time?.text = recipe.time
        image?.text = recipe.image
    }

}