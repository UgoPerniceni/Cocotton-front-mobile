package fr.esgi.cocotton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

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
                (activity as MainActivity?)?.addRecipe()
            }
            R.id.new_recipe_form_button_return -> {
                findNavController().navigate(R.id.action_HomeFragment_to_NewRecipeFragment)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }
}