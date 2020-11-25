package fr.esgi.cocotton

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment


/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {

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

        initializeSpinners()
    }

    private fun initializeSpinners(){
        val sharedPreferences = this.activity?.getSharedPreferences("Settings", Activity.MODE_PRIVATE)

        spinnerLanguages?.apply {
            val languagesArray = resources.getStringArray(R.array.languages_array)
            val adapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, languagesArray)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.adapter = adapter

            this.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>?, view: View, position: Int, l: Long) {
                    when (position) {
                        0 -> (activity as MainActivity?)?.setLocale("eng")
                        1 -> (activity as MainActivity?)?.setLocale("fr")
                        2 -> (activity as MainActivity?)?.setLocale("es")
                        else -> {}
                    }
                }
                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
            val language = sharedPreferences?.getString("language", "eng")

            if(languagesInteger().containsKey(language)){
                languagesInteger()[language]?.let  { it1 -> this.setSelection(it1) }
            }else {
                this.setSelection(1)
            }
        }

        spinnerThemes?.apply {
            val themesArray = resources.getStringArray(R.array.themes_array)
            val adapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, themesArray)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.adapter = adapter
        }
    }

    private fun languagesInteger(): HashMap<String, Int> {
        val translations = hashMapOf<String, Int>()
        translations["eng"] = 0
        translations["fr"] = 1
        translations["es"] = 2

        return translations
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }

}
