package com.example.remindify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class LandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var bottommenu= findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val fragment = PastReminders()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()


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


    }
}