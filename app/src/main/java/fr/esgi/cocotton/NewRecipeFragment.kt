package fr.esgi.cocotton

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import fr.esgi.cocotton.adapter.IngredientAdapter
import fr.esgi.cocotton.adapter.SpinnerIconAdapter
import fr.esgi.cocotton.model.Icon
import fr.esgi.cocotton.model.Ingredient
import fr.esgi.cocotton.model.Recipe

/**
 * A simple [Fragment] subclass.
 * Use the [NewRecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewRecipeFragment : Fragment(), View.OnClickListener {

    private var spinner: Spinner? = null
    private var spinnerIcons: Spinner? = null

    private var pickerHours: NumberPicker? = null
    private var pickerMinutes: NumberPicker? = null

    private var textViewNewRecipe: TextView? = null
    private var textViewIcon: TextView? = null
    private var textViewTime: TextView? = null
    private var textViewIngredient: TextView? = null
    private var textViewStep: TextView? = null
    private var textViewHours: TextView? = null
    private var textViewMinutes: TextView? = null

    private var editTextName: EditText? = null
    private var editTextPerson: EditText? = null
    private var editTextIngredient: EditText? = null
    private var editTextSteps: EditText? = null

    private var recyclerView: RecyclerView? = null

    private val ingredients = mutableListOf<Ingredient>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize(view)

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
                editTextName?.error = if (content.isBlank() || content.length <= 3) getString(R.string.field_required_3_char) else null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        spinnerIcons?.apply {
            val adapter = SpinnerIconAdapter(context, loadDefaultIcons())
            this.adapter = adapter
        }
    }

    private fun initialize(view: View){
        setViewByIds(view)
        setOnClickListeners(view)

        setRecycleView(view)

        setStyles()
    }

    private fun setRecycleView(view: View){
        recyclerView = view.findViewById(R.id.new_recipe_form_recycle_view)

        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = IngredientAdapter(ingredients)

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setViewByIds(view: View){
        spinner = view.findViewById(R.id.new_recipe_form_spinner_difficulty)
        spinnerIcons = view.findViewById(R.id.new_recipe_form_spinner_icons)

        pickerHours = view.findViewById(R.id.new_recipe_form_number_picker_hours)
        pickerMinutes = view.findViewById(R.id.new_recipe_form_number_picker_minutes)

        textViewNewRecipe = view.findViewById(R.id.new_recipe_form_text_view)
        textViewIcon = view.findViewById(R.id.new_recipe_form_text_view_icon)
        textViewTime = view.findViewById(R.id.new_recipe_form_text_view_time)
        textViewIngredient = view.findViewById(R.id.new_recipe_form_text_view_ingredients)
        textViewStep = view.findViewById(R.id.new_recipe_form_text_view_steps)
        textViewHours = view.findViewById(R.id.new_recipe_text_view_hours)
        textViewMinutes = view.findViewById(R.id.new_recipe_text_view_minutes)

        editTextName = view.findViewById(R.id.new_recipe_form_name)
        editTextPerson = view.findViewById(R.id.new_recipe_form_for_number)
        editTextIngredient = view.findViewById(R.id.new_recipe_form_edit_text_create)
        editTextSteps = view.findViewById(R.id.new_recipe_form_edit_text_steps)
    }

    private fun setOnClickListeners(view: View){
        view.findViewById<Button>(R.id.new_recipe_form_button_add_ingredient).setOnClickListener(this)
        view.findViewById<Button>(R.id.new_recipe_form_button_validate).setOnClickListener(this)
        view.findViewById<Button>(R.id.new_recipe_form_button_return).setOnClickListener(this)
    }

    private fun setStyles(){
        textViewNewRecipe?.setTypeface(null, Typeface.BOLD)

        textViewIcon?.setTypeface(null, Typeface.ITALIC)
        textViewTime?.setTypeface(null, Typeface.ITALIC)
        textViewIngredient?.setTypeface(null, Typeface.ITALIC)
        textViewStep?.setTypeface(null, Typeface.ITALIC)
    }

    private fun setUpTextHours(counter: Int) {
        val itemsFound = resources.getQuantityString(R.plurals.countHours, counter, counter)
        textViewHours?.apply { text = itemsFound }
    }

    private fun setUpTextMinutes(counter: Int) {
        val itemsFound = resources.getQuantityString(R.plurals.countMinutes, counter, counter)
        textViewMinutes?.apply { text = itemsFound }
    }

    private fun formIsValid(): Boolean {
        var validateForm = true

        editTextName?.let {
            if(TextUtils.isEmpty(it.text.toString())){
                it.error = getString(R.string.field_required_3_char)
                validateForm = false
            }
        }

        editTextPerson?.let {
            if(TextUtils.isEmpty(it.text.toString())){
                it.error = getString(R.string.field_required)
                validateForm = false
            }
        }

        editTextSteps?.let {
            if(TextUtils.isEmpty(it.text.toString())){
                it.error = getString(R.string.field_required)
                validateForm = false
            }
        }

        return validateForm
    }

    private fun createNewRecipe(){
        if(formIsValid()){
            val userConnected : FirebaseUser? = (activity as MainActivity).mAuth.currentUser

            val name = view?.findViewById<EditText>(R.id.new_recipe_form_name)
            val icon: Icon = view?.findViewById<Spinner>(R.id.new_recipe_form_spinner_icons)?.selectedItem as Icon
            val forPerson = view?.findViewById<EditText>(R.id.new_recipe_form_for_number)
            val difficulty = view?.findViewById<Spinner>(R.id.new_recipe_form_spinner_difficulty)
            val hours = pickerHours?.value?.times(60)
            val minutes = pickerMinutes?.value
            val time = minutes?.let { hours?.plus(it) }
            val ingredients: List<Ingredient> = ingredients
            val steps = view?.findViewById<EditText>(R.id.new_recipe_form_edit_text_steps)

            val authorDN = userConnected?.displayName
            val authorEmail = userConnected?.email

            val newRecipe = Recipe("${name?.text}", time?.toLong(), ("${forPerson?.text}").toLong(), "${difficulty?.selectedItem}", icon.drawable, ingredients, steps?.text.toString().replace("\n", "\\n"),"$authorDN",  "$authorEmail")
            newRecipe.saveToDb()

            findNavController().navigate(R.id.action_NewRecipeFragment_to_HomeFragment)

        }else{
            Toast.makeText(context, getString(R.string.fields_required), Toast.LENGTH_SHORT).show()
        }
    }

    private fun createNewIngredient(){
        editTextIngredient?.let {
            if(TextUtils.isEmpty(it.text.toString())){
                it.error = getString(R.string.field_required)
            }else{
                ingredients.add(Ingredient(it.text.toString()))
            }
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.new_recipe_form_button_add_ingredient -> {
                createNewIngredient()
                recyclerView?.adapter?.notifyDataSetChanged()
            }
            R.id.new_recipe_form_button_validate -> {
                createNewRecipe()
            }
            R.id.new_recipe_form_button_return -> {
                findNavController().navigate(R.id.action_NewRecipeFragment_to_HomeFragment)
            }
        }
    }

    private fun loadDefaultIcons(): List<Icon> {
        val listOfIcons = mutableListOf<Icon>()

        listOfIcons.add(Icon("Cooker", R.drawable.cooker_icon))
        listOfIcons.add(Icon("Cooker open", R.drawable.cooker_open_icon))
        listOfIcons.add(Icon("Cleaver", R.drawable.cleaver_icon))
        listOfIcons.add(Icon("Frying pan", R.drawable.frying_pan_icon))
        listOfIcons.add(Icon("Ingredients", R.drawable.ingredients_icon))
        listOfIcons.add(Icon("Mitten", R.drawable.mitten_icon))

        return listOfIcons;
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }
}