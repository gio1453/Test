package com.example.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }


    private fun init() {
        signInButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = logInEmailEditText.text.toString()
        val password = logInPasswordEditText.text.toString()
        progressBarLogin.isVisible = true
        openDashboard()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("login", "signInWithEmail:success")
                        Toast.makeText(this, "Successfull Login", Toast.LENGTH_SHORT).show()
                        progressBarLogin.isVisible = false
                    } else {
                        Log.d("login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, task.exception.toString().split(":")[1],
                            Toast.LENGTH_LONG
                        ).show()
                        progressBarLogin.isVisible = false
                    }
                    logInEmailEditText.setText("")
                    logInPasswordEditText.setText("")
                }
        } else {
            Toast.makeText(this, "Fill Unfilled Fields", Toast.LENGTH_SHORT).show()
            progressBarLogin.isVisible = false
        }


    }

    private fun openDashboard() {
        val intent = Intent(this, FirestoreActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

    }
}

