package fr.esgi.cocotton.model


data class Recipe(val name: String, val time: Long? =  0, val forPerson: Long =  0, val difficulty: String  =  "", val icon: Number = 0, val ingredients: List<Ingredient>, val steps: String = "", val authorDisplayName: String = "", val authorEmail: String = "") {
    var id: Int = 0

    fun saveToDb(){
        val dbConnection = DatabaseConnection()
        dbConnection.save("recipes", this)
    }

    override fun toString(): String {
        return "Recipe($id, $name, $time, $forPerson, $difficulty, $icon, $ingredients, $steps, $authorDisplayName, $authorEmail)"
    }
}