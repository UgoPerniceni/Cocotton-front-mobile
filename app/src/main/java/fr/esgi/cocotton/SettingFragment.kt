package fr.esgi.cocotton

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment(), View.OnClickListener {

    private var spinnerLanguages: Spinner? = null
    private var spinnerThemes: Spinner? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerLanguages = view.findViewById(R.id.spinner_language)
        spinnerThemes = view.findViewById(R.id.spinner_theme)

        view.findViewById<Button>(R.id.button_fragment_setting_disconnect).setOnClickListener(this)

        initializeSpinners()
    }

    private fun initializeSpinners(){
        val sharedPreferences = this.activity?.getSharedPreferences("Settings", Activity.MODE_PRIVATE)

        spinnerLanguages?.apply {
            val languagesArray = resources.getStringArray(R.array.languages_array)
            val adapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, languagesArray)
            val language = sharedPreferences?.getString("language", "eng")

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.adapter = adapter

            if(languagesToInteger().containsKey(language)){
                languagesToInteger()[language]?.let  { it -> this.setSelection(it) }
            }else {
                this.setSelection(0)
            }

            this.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        0 -> {
                            (activity as MainActivity).setLocale("eng")
                            Toast.makeText(context, "Current language is English", Toast.LENGTH_SHORT).show()
                        }
                        1 -> {
                            (activity as MainActivity).setLocale("fr")
                            Toast.makeText(context, "Current language is French", Toast.LENGTH_SHORT).show()
                        }
                        2 -> {
                            (activity as MainActivity).setLocale("es")
                            Toast.makeText(context, "Current language is Spanish", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        }

        spinnerThemes?.apply {
            val themesArray = resources.getStringArray(R.array.themes_array)
            val adapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, themesArray)
            val theme = sharedPreferences?.getInt("theme", 0)

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.adapter = adapter

            theme?.let { this.setSelection(it) }

            this.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        0 -> (activity as MainActivity).setThemeMode(0)
                        1 -> (activity as MainActivity).setThemeMode(1)
                        2 -> (activity as MainActivity).setThemeMode(2)
                        else -> {}
                    }
                }
                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        }
    }

    private fun languagesToInteger(): HashMap<String, Int> {
        val translations = hashMapOf<String, Int>()
        translations["eng"] = 0
        translations["fr"] = 1
        translations["es"] = 2

        return translations
    }

    private fun disconnect(){
        (activity as MainActivity).mAuth.signOut()
        (activity as MainActivity).googleSignClient.signOut()

        findNavController().navigate(R.id.action_SettingFragment_to_LoginFragment)

        Toast.makeText(context, "Successfully disconnected.", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.button_fragment_setting_disconnect -> {
                disconnect()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }

}
