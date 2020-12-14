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
        val hours = recipe.time?.div(60)
        val minutes = recipe.time?.rem(60)

        name?.text = recipe.name

        if (hours?: 0 > 0){
            time?.text = "${hours}h and ${minutes} min"
        }else{
            time?.text = "${minutes} min"
        }

        image?.text = recipe.image
    }

}