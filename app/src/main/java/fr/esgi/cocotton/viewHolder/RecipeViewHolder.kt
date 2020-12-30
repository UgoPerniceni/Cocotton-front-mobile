package fr.esgi.cocotton.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.cocotton.R
import fr.esgi.cocotton.model.Recipe

class RecipeViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(
        inflater.inflate(R.layout.recipe_item, parent, false)
    )
{

    private var image: ImageView? = null

    private var name: TextView? = null
    private var time: TextView? = null
    private var difficulty: TextView? = null
    private var author: TextView? = null

    init
    {
        image = itemView.findViewById(R.id.recipe_item_image)
        name = itemView.findViewById(R.id.recipe_item_name)
        time = itemView.findViewById(R.id.recipe_item_time)
        difficulty = itemView.findViewById(R.id.recipe_item_difficulty)
        author = itemView.findViewById(R.id.recipe_item_author)
    }

    fun bind(recipe: Recipe)
    {
        name?.text = recipe.name
        difficulty?.text = recipe.difficulty
        author?.text = recipe.authorDisplayName

        time?.text = formatTime(recipe.time)

        image?.setImageResource(recipe.icon.toInt())
    }

    private fun formatTime(timestamp: Long?): String{
        timestamp?.let {
            val hours = it.div(60)
            val minutes = it.rem(60)

            return if (hours > 0){
                "${hours}h${minutes}m"
            }else{
                "${minutes}m"
            }
        }
        return "0m"
    }
}