package fr.esgi.cocotton

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseUser


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onStart() {
        super.onStart()
        context?.let { (activity as MainActivity).isOnline(it) }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.button_first -> {
                val user : FirebaseUser? = (activity as MainActivity).mAuth.currentUser

                user?.let {
                    for (profile in it.providerData) {
                        // Id of the provider (ex: google.com)
                        val providerId = profile.providerId

                        // UID specific to the provider
                        //val uid = profile.uid

                        // Name, email address, and profile photo Url
                        val name = profile.displayName
                        val email = profile.email
                        // val photoUrl = profile.photoUrl

                        Toast.makeText(context,
                                "Connected as : $name\n" +
                                "with : $email\n" +
                                        "on $providerId", Toast.LENGTH_SHORT).show()
                    }
                }

                if(user != null){
                    findNavController().navigate(R.id.action_LoginFragment_to_HomeFragment)
                }else{
                    (activity as MainActivity).signIn()
                }
            }
        }
    }
}