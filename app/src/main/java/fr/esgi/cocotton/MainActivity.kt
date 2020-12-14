package fr.esgi.cocotton

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.esgi.cocotton.model.Recipe
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var loader: ProgressBar? = null
    private var fadeScreen: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler().postDelayed({
            if(user == null){
                val signInIntent = Intent(this, SignInActivity::class.java)
                startActivity(signInIntent)
            }
        }, 2000)

        loadLocate()

/*        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

        loader = findViewById(R.id.loader)
        fadeScreen = findViewById(R.id.fadeScreen)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                // TODO()
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
}