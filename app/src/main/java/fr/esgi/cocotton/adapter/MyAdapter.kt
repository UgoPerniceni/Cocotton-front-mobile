package fr.esgi.cocotton.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.cocotton.R
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

        holder.itemView.setOnClickListener { view ->
            Toast.makeText(view.context, position.toString(), Toast.LENGTH_SHORT).show()

            val bundle = bundleOf(
                "name" to recipe.name,
                "difficulty" to recipe.difficulty
            )

            view.findNavController().navigate(R.id.action_HomeFragment_to_RecipeFragment, bundle)
        }
    }

    override fun getItemCount(): Int = recipes.size
}