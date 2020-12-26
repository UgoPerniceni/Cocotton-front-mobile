package fr.esgi.cocotton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeFragment : Fragment() {

    private var textViewTitle: TextView? = null
    private var textViewAuthor: TextView? = null
    private var textViewDifficulty: TextView? = null
    private var textViewTimeRequired: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewTitle = view.findViewById(R.id.recipe_text_view_title)
        textViewAuthor = view.findViewById(R.id.recipe_text_view_author)
        textViewDifficulty = view.findViewById(R.id.recipe_text_view_difficulty)
        textViewTimeRequired = view.findViewById(R.id.recipe_text_view_time_required)

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
            this.text = arguments?.getString("timeRequired")
        }

    }
}