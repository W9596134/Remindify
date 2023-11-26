package com.example.remindify

import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ReminderAlarm : AppCompatActivity() {
    private var notificationRingtone: Ringtone? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_receiver)

        // Get the default notification sound
        val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // Create a Ringtone object
        notificationRingtone = RingtoneManager.getRingtone(applicationContext, notificationSound)

        // Play the notification sound
        notificationRingtone?.play()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Stop the notification sound and release resources when the activity is destroyed
        notificationRingtone?.stop()
    }
}