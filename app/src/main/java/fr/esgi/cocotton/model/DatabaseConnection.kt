package fr.esgi.cocotton.model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DatabaseConnection {
    val db: FirebaseFirestore = Firebase.firestore

    init {
        // Access a Cloud Firestore instance
    }

    fun save(collection: String, obj: Any){
        // Add a new document with a generated ID
        db.collection(collection)
                .add(obj)
                .addOnSuccessListener { documentReference ->
                    Log.d("onSuccess", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("onFailure", "Error adding document", e)
                }
    }
}