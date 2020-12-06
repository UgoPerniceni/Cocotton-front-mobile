package fr.esgi.cocotton

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.esgi.cocotton.model.Recipe

/**
 * A simple [Fragment] subclass.
 * Use the [NewRecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewRecipeFragment : Fragment(), View.OnClickListener {

    private var spinner: Spinner? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.new_recipe_form_button_validate).setOnClickListener(this)

        spinner = view.findViewById(R.id.new_recipe_form_spinner_difficulty)

        spinner?.apply {
            val difficultiesArray = resources.getStringArray(R.array.difficulty_array)
            val adapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, difficultiesArray)

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.adapter = adapter
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.new_recipe_form_button_validate -> {
                createNewRecipe()
            }
            R.id.new_recipe_form_button_return -> {
                findNavController().navigate(R.id.action_HomeFragment_to_NewRecipeFragment)
            }
        }
    }

    private fun createNewRecipe(){
        val name = view?.findViewById<EditText>(R.id.new_recipe_form_name)
        val time = view?.findViewById<EditText>(R.id.new_recipe_form_time)
        val forPerson = view?.findViewById<EditText>(R.id.new_recipe_form_for_number)
        val difficulty = view?.findViewById<Spinner>(R.id.new_recipe_form_spinner_difficulty)

        val newRecipe = Recipe("${name?.text}", "${time?.text}", ("${forPerson?.text}").toLong(), "${difficulty?.selectedItem}", "/path")

        newRecipe.saveToDb()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }
}