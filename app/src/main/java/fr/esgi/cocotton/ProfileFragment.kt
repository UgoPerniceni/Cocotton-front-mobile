package fr.esgi.cocotton

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.io.InputStream
import java.net.URL

class ProfileFragment : Fragment() {

    private var userName: TextView? = null
    private var userEmail: TextView? = null

    private var userIcon: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = Firebase.auth.currentUser

        userName = view.findViewById(R.id.user_display_name)
        userName?.text = user?.displayName

        userEmail = view.findViewById(R.id.user_email)
        userEmail?.text = user?.email

        userIcon = view.findViewById(R.id.fragment_profile_user_icon)
        val imageUrl = user?.photoUrl.toString()

        Picasso.get().load(imageUrl).into(userIcon)
    }

    override fun onStart() {
        super.onStart()
        context?.let { (activity as MainActivity).isOnline(it) }
    }
}
