package com.example.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_firestore.*

class FirestoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firestore)

        saveButton.setOnClickListener {
            val headline = inputFirstName.text.toString()
            val text = inputLastName.text.toString()

            saveFireStore(headline, text)

        }
        readFireStoreData()
    }

    fun saveFireStore(Headline: String, Text: String) {
        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["Headline"] = Headline
        user["Text"] = Text

        db.collection("info")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this@FirestoreActivity, "record added successfully ", Toast.LENGTH_SHORT ).show()
            }
            .addOnFailureListener{
                Toast.makeText(this@FirestoreActivity, "record Failed to add ", Toast.LENGTH_SHORT ).show()
            }
        readFireStoreData()
    }

    fun readFireStoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("info")
            .get()
            .addOnCompleteListener {

                val result: StringBuffer = StringBuffer()

                if(it.isSuccessful) {
                    for(document in it.result!!) {
                        result.append(document.data.getValue("Headline")).append("\n")
                            .append(document.data.getValue("Text")).append("\n\n")
                    }
                    textViewResult.setText(result)
                }
            }

    }

}