package fr.esgi.cocotton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Use the [NewRecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewRecipeFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.new_recipe_form_button_validate).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.new_recipe_form_button_validate -> {
                (activity as MainActivity?)?.addRecipe()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }
}