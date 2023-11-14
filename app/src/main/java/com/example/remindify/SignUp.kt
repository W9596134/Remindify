package com.example.remindify

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //connect all the views in the xml with variavbles
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.pass)
        val password2 = findViewById<EditText>(R.id.pass2)
        val signUp = findViewById<Button>(R.id.signup)
        val signin = findViewById<TextView>(R.id.signin)

        signUp.setOnClickListener {
            //get the text from the email and password fields
            val emailText = email.text.toString()
            val passwordText = password.text.toString()
            val password2Text = password2.text.toString()

            //check if the password and confirm password are the same
            if (passwordText == password2Text) {
                //create a new user with the email and password
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener {
                        //if the user is created successfully
                        if (it.isSuccessful) {
                            //send user to the main activity
                            startActivity(Intent(this, LandingPage::class.java))
                            //finish this activity
                            finish()
                        } else {
                            //if the user is not created successfully
                            //display a toast message to user with the exception message
                            Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                //if the passwords are not the same
                //display a toast message to user
                Toast.makeText(this, "Password don't match", Toast.LENGTH_SHORT).show()
            }
        }
        signin.setOnClickListener {
            //send user to the sign in activity
            var intent =Intent(this, SignIn::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
            //finish this activity
            finish()
        }





    }
}