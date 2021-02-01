package fr.esgi.cocotton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.cocotton.adapter.IngredientAdapter
import fr.esgi.cocotton.model.Ingredient
import fr.esgi.cocotton.model.Recipe

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeFragment : Fragment(), View.OnClickListener {

    private var imageIcon: ImageView? = null

    private var recyclerViewIngredients: RecyclerView? = null

    private var textViewTitle: TextView? = null
    private var textViewAuthor: TextView? = null
    private var textViewDifficulty: TextView? = null
    private var textViewTimeRequired: TextView? = null
    private var textViewSteps: TextView? = null

    private var textViewPersons: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageIcon = view.findViewById(R.id.recipe_text_view_icon)

        recyclerViewIngredients = view.findViewById(R.id.recipe_recycle_view)

        textViewTitle = view.findViewById(R.id.recipe_text_view_title)
        textViewAuthor = view.findViewById(R.id.recipe_text_view_author)
        textViewDifficulty = view.findViewById(R.id.recipe_text_view_difficulty)
        textViewTimeRequired = view.findViewById(R.id.recipe_text_view_time_required)
        textViewPersons = view.findViewById(R.id.recipe_text_view_for_persons)

        textViewSteps = view.findViewById(R.id.recipe_edit_text_steps)

        view.findViewById<Button>(R.id.recipe_button_return).setOnClickListener(this)

        imageIcon?.apply {
            this.setImageResource((arguments?.get("icon") as Long).toInt())
        }

        recyclerViewIngredients?.apply {
            val ingredients = mutableListOf<Ingredient>()

            val arrayList = arguments?.get("ingredients") as ArrayList<*>

            arrayList.forEach{ hashMap ->
                (hashMap as HashMap<*, *>).values.forEach{ name ->
                    ingredients.add(Ingredient(name as String))
                }
            }
            
            layoutManager = LinearLayoutManager(this.context)
            adapter = IngredientAdapter(ingredients)

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        textViewTitle?.apply {
            this.text = arguments?.getString("name")
        }
        textViewAuthor?.apply {
            this.text = arguments?.getString("author")
        }
        textViewDifficulty?.apply {
            this.text = arguments?.getString("difficulty")
        }
        textViewTimeRequired?.apply {
            val timestamp: Long? = arguments?.getLong("timeRequired")

            timestamp?.let {
                this.text = getString(R.string.time_required_, formatTime(timestamp))
            }
        }

        textViewPersons?.apply {
            val person: Long? = arguments?.getLong("person")
            person?.let {
                this.text = resources.getQuantityString(R.plurals.for_persons, it.toInt(), it)
            }
        }

        textViewSteps?.apply {
            val steps: String? = arguments?.getString("steps")?.replace("\\n", "\n")
            this.text = steps
        }
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

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.recipe_button_return -> {
                findNavController().navigate(R.id.action_RecipeFragment_to_HomeFragment)
            }
        }
    }
}