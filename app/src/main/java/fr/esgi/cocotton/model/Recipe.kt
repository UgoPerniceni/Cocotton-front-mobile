package fr.esgi.cocotton.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(val name: String, val time: String  =  "", val image: String = "") {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "Recipe($id, $name, $time, $image)"
    }
}