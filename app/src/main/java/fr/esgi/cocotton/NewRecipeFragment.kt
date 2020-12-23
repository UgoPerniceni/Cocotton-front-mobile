package fr.esgi.cocotton

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.esgi.cocotton.model.Recipe

/**
 * A simple [Fragment] subclass.
 * Use the [NewRecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewRecipeFragment : Fragment(), View.OnClickListener {

    private var spinner: Spinner? = null
    private var pickerHours: NumberPicker? = null
    private var pickerMinutes: NumberPicker? = null

    private var textViewHours: TextView? = null
    private var textViewMinutes: TextView? = null

    private var editTextName: EditText? = null
    private var editTextPerson: EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.new_recipe_form_button_validate).setOnClickListener(this)
        view.findViewById<Button>(R.id.new_recipe_form_button_return).setOnClickListener(this)

        spinner = view.findViewById(R.id.new_recipe_form_spinner_difficulty)
        pickerHours = view.findViewById(R.id.new_recipe_form_number_picker_hours)
        pickerMinutes = view.findViewById(R.id.new_recipe_form_number_picker_minutes)

        textViewHours = view.findViewById(R.id.new_recipe_text_view_hours)
        textViewMinutes = view.findViewById(R.id.new_recipe_text_view_minutes)

        editTextName = view.findViewById(R.id.new_recipe_form_name)
        editTextPerson = view.findViewById(R.id.new_recipe_form_for_number)

        spinner?.apply {
            val difficultiesArray = resources.getStringArray(R.array.difficulty_array)
            val adapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, difficultiesArray)

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.adapter = adapter
        }

        pickerHours?.apply {
            this.minValue = 0
            this.maxValue = 168

            this.setOnValueChangedListener { _, _, newVal ->
                setUpTextHours(newVal)
            }
        }

        pickerMinutes?.apply {
            this.minValue = 0
            this.maxValue = 60

            this.setOnValueChangedListener { _, _, newVal ->
                setUpTextMinutes(newVal)
            }
        }

        editTextName?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                val content = text.toString()
                editTextName?.error = if (content.isBlank() || content.length <= 3) "Veuillez remplir ce champ (3 char+)" else null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }

    private fun setUpTextHours(counter: Int) {
        val itemsFound = resources.getQuantityString(R.plurals.countHours, counter, counter)
        textViewHours?.apply { text = itemsFound }
    }

    private fun setUpTextMinutes(counter: Int) {
        val itemsFound = resources.getQuantityString(R.plurals.countMinutes, counter, counter)
        textViewMinutes?.apply { text = itemsFound }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.new_recipe_form_button_validate -> {
                createNewRecipe()
            }
            R.id.new_recipe_form_button_return -> {
                findNavController().navigate(R.id.action_NewRecipeFragment_to_HomeFragment)
            }
        }
    }

    private fun formIsValid(): Boolean {
        var validateForm = true

        editTextName?.let {
            if(TextUtils.isEmpty(it.text.toString())){
                it.error = "Veuillez remplir ce champ (3 char+)"
                validateForm = false
            }
        }

        editTextPerson?.let {
            if(TextUtils.isEmpty(it.text.toString())){
                it.error = "Veuillez remplir ce champ"
                validateForm = false
            }
        }

        return validateForm
    }

    private fun createNewRecipe(){
        if(formIsValid()){
            val name = view?.findViewById<EditText>(R.id.new_recipe_form_name)
            val forPerson = view?.findViewById<EditText>(R.id.new_recipe_form_for_number)
            val difficulty = view?.findViewById<Spinner>(R.id.new_recipe_form_spinner_difficulty)
            val hours = pickerHours?.value?.times(60)
            val minutes = pickerMinutes?.value
            val time = minutes?.let { hours?.plus(it) }

            val newRecipe = Recipe("${name?.text}", time?.toLong(), ("${forPerson?.text}").toLong(), "${difficulty?.selectedItem}", "/path")

            newRecipe.saveToDb()

            findNavController().navigate(R.id.action_NewRecipeFragment_to_HomeFragment)

        }else{
            Toast.makeText(context, "Remplir les champs svp", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }
}