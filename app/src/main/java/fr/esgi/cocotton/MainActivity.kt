package fr.esgi.cocotton

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.esgi.cocotton.model.Recipe
import java.util.*


class MainActivity : AppCompatActivity() {
    var defaultLanguage = "en"

    private var loader: ProgressBar? = null
    private var fadeScreen: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

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
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, SettingFragment.newInstance())
                    .commitNow()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun addRecipe() {
        val name = findViewById<EditText>(R.id.new_recipe_form_name)
        val time = findViewById<EditText>(R.id.new_recipe_form_time)

        val newRecipe = Recipe("${name.text}", "${time.text}", "/path")

        // Access a Cloud Firestore instance from your Activity
        val db = Firebase.firestore

        // Add a new document with a generated ID
        db.collection("recipes")
            .add(newRecipe)
            .addOnSuccessListener { documentReference ->
                Log.d("onSuccess", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("onFailure", "Error adding document", e)
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

    fun updateLanguage(newLanguage: String) {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        if(sharedPreferences.contains("language")){
            if (newLanguage != sharedPreferences.getString("language", "eng")) {
                val newLocaleConf : Locale = Locale(newLanguage)
                val resources: Resources = resources
                val dm: DisplayMetrics = resources.displayMetrics
                val conf: Configuration = resources.configuration
                conf.setLocale(newLocaleConf)
                resources.updateConfiguration(conf, dm)
                sharedPreferences.edit().putString("language", newLanguage).apply();

                val refresh = Intent(this, MainActivity::class.java)
                refresh.putExtra(defaultLanguage, newLanguage)
                startActivity(refresh)
            } else {
                Toast.makeText(this, "Language already selected!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}