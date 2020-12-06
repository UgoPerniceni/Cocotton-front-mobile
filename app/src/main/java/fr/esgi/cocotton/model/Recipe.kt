package fr.esgi.cocotton.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Entity
data class Recipe(val name: String, val time: String  =  "", val forPerson: Long =  0, val difficulty: String  =  "", val image: String = "") {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun saveToDb(){
        val dbConnection = DatabaseConnection()
        dbConnection.save("recipes", this)
    }

    override fun toString(): String {
        return "Recipe($id, $name, $time, $forPerson, $difficulty, $image)"
    }
}