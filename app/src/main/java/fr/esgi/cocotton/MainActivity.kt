package fr.esgi.cocotton

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.util.*


class MainActivity : AppCompatActivity(){

    companion object {
        private const val RC_SIGN_IN = 123
    }

    private var loader: ProgressBar? = null
    private var fadeScreen: View? = null

    lateinit var mAuth: FirebaseAuth
    lateinit var googleSignClient: GoogleSignInClient

    lateinit var mOptionsMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        loadLocate()

        loader = findViewById(R.id.loader)
        fadeScreen = findViewById(R.id.fadeScreen)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()
    }

    fun signIn() {
        val signInIntent = googleSignClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful){
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("SignInActivity", exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(this) {
                task ->
            if(task.isSuccessful) {
                Toast.makeText(this, "You have been successfully authenticated.\nPlease click on log in.", Toast.LENGTH_SHORT).show()
                Log.d("SignInActivity", "signInWithCredential:success")
            } else {
                Log.d("SignInActivity", "signInWithCredential:failure")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        mOptionsMenu = menu

        menuInflater.inflate(R.menu.menu_main, menu)
/*
        menu.getItem(0).isEnabled = false*/
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_home -> {
                val navController = findNavController(R.id.nav_host_fragment)

                navController.navigate(R.id.HomeFragment)
                return true
            }
            R.id.action_profile -> {
                val navController = findNavController(R.id.nav_host_fragment)

                navController.navigate(R.id.ProfileFragment)
                return true
            }
            R.id.action_settings -> {
                val navController = findNavController(R.id.nav_host_fragment)

                navController.navigate(R.id.SettingFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showLoader() {
        fadeScreen?.isVisible = true
        loader?.isVisible = true
    }

    fun hideLoader() {
        fadeScreen?.isVisible = false
        loader?.isVisible = false
    }

    fun setLocale(newLanguage: String) {
        val locale = Locale(newLanguage)
        val config = Configuration()

        Locale.setDefault(locale)
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Activity.MODE_PRIVATE).edit()
        editor.putString("language", newLanguage).apply()
    }

    private fun loadLocate(){
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("language", "eng")
        val theme = sharedPreferences.getInt("theme", 0)

        setThemeMode(theme)
        language?.let { setLocale(it) }
    }

    fun setThemeMode(choice: Int) {
        val editor = getSharedPreferences("Settings", Activity.MODE_PRIVATE).edit()
        when (choice) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()

                editor.putInt("theme", 0).apply()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()

                editor.putInt("theme", 1).apply()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()

                editor.putInt("theme", 2).apply()
            }
        }
    }

    fun isOnline(context: Context) {
        var connection = false

        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    connection = true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    connection = true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    connection = true
                }
            }

        if(!connection){
            Toast.makeText(this, "You are currently offline !\nPlease Log in to save your data.", Toast.LENGTH_SHORT).show()
        }
    }
}