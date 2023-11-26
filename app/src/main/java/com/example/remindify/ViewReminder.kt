package com.example.remindify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ViewReminder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reminder)

        var title=findViewById<TextView>(R.id.title)
        var des=findViewById<TextView>(R.id.des)
        var date=findViewById<TextView>(R.id.date)
        var time=findViewById<TextView>(R.id.time)
        var image=findViewById<ImageView>(R.id.image)

        title.text=intent.getStringExtra("title")
        des.text=intent.getStringExtra("des")
        date.text=intent.getStringExtra("date")
        time.text=intent.getStringExtra("time")
        if(intent.getStringExtra("image")!="")
            Picasso.get().load(intent.getStringExtra("image")).into(image)


    }
}