package com.example.remindify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.pass)
        val signin = findViewById<Button>(R.id.signin)
        val signup = findViewById<TextView>(R.id.signup)
        
        signin.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()
            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        var intent=Intent(this, LandingPage::class.java);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }

        signup.setOnClickListener {
            var intent=Intent(this,SignUp::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}