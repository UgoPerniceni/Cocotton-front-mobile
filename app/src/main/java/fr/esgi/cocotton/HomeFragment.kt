package fr.esgi.cocotton

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.esgi.cocotton.adapter.MyAdapter
import fr.esgi.cocotton.model.Recipe

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment(), View.OnClickListener {

    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener(this)
        view.findViewById<Button>(R.id.button_third).setOnClickListener(this)
        view.findViewById<Button>(R.id.button_new_recipe).setOnClickListener(this)

        recyclerView = view.findViewById(R.id.recyclerView_recipes)

        val recipes = mutableListOf<Recipe>()
        val db = Firebase.firestore

        (activity as MainActivity?)?.showLoader()

        db.collection("recipes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("--- success ---", "${document.id} => ${document.data}")
                    recipes.add(Recipe(document.data["name"] as String, document.data["time"] as String, document.data["image"] as String))
                }
            }
            .addOnFailureListener { exception ->
                Log.w("failure", "Error getting documents.", exception)
            }

            .addOnCompleteListener {
                recyclerView?.apply {
                    layoutManager = LinearLayoutManager(this.context)
                    adapter = MyAdapter(recipes)
                }

                (activity as MainActivity?)?.hideLoader()
            }
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