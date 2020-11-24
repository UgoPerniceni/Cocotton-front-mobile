package fr.esgi.cocotton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {

    private var spinner: Spinner? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = view.findViewById(R.id.spinner_language)

        initializeSpinner()
    }

    private fun initializeSpinner(){
        spinner?.apply {
            val languagesArray = resources.getStringArray(R.array.languages_array)
            val adapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, languagesArray)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.adapter = adapter

            this.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>?, view: View, position: Int, l: Long) {
                    when (position) {
                        1 -> (activity as MainActivity?)?.setLocale("eng")
                        2 -> (activity as MainActivity?)?.setLocale("es")
                        3 -> (activity as MainActivity?)?.setLocale("fr")
                        else -> {}
                    }
                }
                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }

}
