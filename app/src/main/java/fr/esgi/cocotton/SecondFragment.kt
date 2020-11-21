package fr.esgi.cocotton

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener(this)
        view.findViewById<Button>(R.id.button_third).setOnClickListener(this)
        view.findViewById<Button>(R.id.button_new_recipe).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.button_second -> {
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
            R.id.button_third -> {
                findNavController().navigate(R.id.action_SecondFragment_to_SettingFragment)
            }
            R.id.button_new_recipe -> {
                findNavController().navigate(R.id.action_SecondFragment_to_NewRecipeFragment)
            }
        }
    }
}