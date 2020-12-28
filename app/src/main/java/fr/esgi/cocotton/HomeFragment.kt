package fr.esgi.cocotton

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.esgi.cocotton.adapter.RecipeAdapter
import fr.esgi.cocotton.model.Icon
import fr.esgi.cocotton.model.Ingredient
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

        view.findViewById<Button>(R.id.button_fragment_home_new_recipe).setOnClickListener(this)

        recyclerView = view.findViewById(R.id.recyclerView_recipes)

        val recipes = mutableListOf<Recipe>()
        val db = Firebase.firestore

        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = RecipeAdapter(recipes)
        }

        (activity as MainActivity).showLoader()

        db.collection("recipes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("--- success ---", "${document.id} => ${document.data}")

                    recipes.add(
                            Recipe(
                                document.data["name"] as String,
                                document.data["time"] as Long,
                                document.data["forPerson"] as Long,
                                document.data["difficulty"] as String,
                                    document.data["icon"] as Number,
                                    document.data["ingredients"] as List<Ingredient>,
                                    document.data["steps"] as String,
                                document.data["authorDisplayName"] as String,
                                document.data["authorEmail"] as String
                            )
                    )
                }
            }
            .addOnFailureListener { exception ->
                Log.w("failure", "Error getting documents.", exception)
            }
            .addOnCompleteListener {
                recyclerView?.apply {
                    this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                }

                (activity as MainActivity).hideLoader()
            }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.button_fragment_home_new_recipe -> {
                findNavController().navigate(R.id.action_HomeFragment_to_NewRecipeFragment)
            }
        }
    }
}