package fr.esgi.cocotton

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.button_first -> {

                val user : FirebaseUser? = (activity as MainActivity).mAuth.currentUser

                user.let { Toast.makeText(context, "Connected as : $user", Toast.LENGTH_SHORT).show() }

                if(user != null){
                    findNavController().navigate(R.id.action_LoginFragment_to_SecondFragment)
                }else{
                    (activity as MainActivity).signIn()
                }
            }
        }
    }
}