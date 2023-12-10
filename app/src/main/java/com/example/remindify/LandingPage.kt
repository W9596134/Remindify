package com.example.remindify

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class LandingPage : AppCompatActivity() {
    var mAuth= FirebaseAuth.getInstance()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var logout= findViewById<TextView>(R.id.logout)
        var bottommenu= findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val fragment = PastReminders()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()

        logout.setOnClickListener {
            mAuth.signOut()
            var intent= Intent(this,SignIn::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        bottommenu.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.past -> {
                    val fragment = PastReminders()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
                }
                R.id.all -> {
                    val fragment = AllReminders()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
                }
                R.id.todo -> {
                    val fragment = Tdo()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
                }
            }
            true
        }
//
//        bottommenu.getOrCreateBadge(R.id.todo).apply {
//            number=10
//            isVisible=true
//        }
        var intent= IntentFilter("myalarm")
        registerReceiver(MyReceiver(),intent)

    }
}