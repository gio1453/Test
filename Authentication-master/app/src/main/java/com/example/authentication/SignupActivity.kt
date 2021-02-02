package com.example.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        init()
    }

    private fun init() {
        auth = Firebase.auth
        signUpButton.setOnClickListener {
            signUp()
        }

    }

    private fun signUp() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val repeatPassword = repeatEditText.text.toString()
        if (emailEditText.text.toString().isNotEmpty() && passwordEditText.text.toString().isNotEmpty() && repeatEditText.text.toString().isNotEmpty()) {
            if ("@" !in email || "." !in email) {
                Toast.makeText(this, "Email format is not Correct", Toast.LENGTH_SHORT).show()
                progressBar.isVisible = false
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (password == repeatEditText.text.toString()) {
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    d("signup", "createUserWithEmail:success")
                                    Toast.makeText(this, "Successful Sign Up", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    openDashboard()
                                } else {
                                    // If sign in fails, display a message to the user.
                                    d("signup", "createUserWithEmail:failure", task.exception)
                                    Toast.makeText(baseContext, task.exception.toString().split(":")[1],
                                            Toast.LENGTH_LONG).show()

                                }
                            } else {
                                Toast.makeText(this, "Passwords Doesn't Match", Toast.LENGTH_SHORT).show()
                                progressBar.isVisible = false
                            }
                            emailEditText.setText("")
                            passwordEditText.setText("")
                            repeatEditText.setText("")


                        }
            }
        } else {
            Toast.makeText(this, "Fill Unfilled Fields", Toast.LENGTH_SHORT).show()
            progressBar.isVisible = false
        }


    }

    private fun openDashboard() {
        val intent = Intent(this, FirestoreActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
