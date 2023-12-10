package com.example.remindify

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var time = intent.getStringExtra("time")
        var title = intent.getStringExtra("title")
        var description = intent.getStringExtra("description")
        var image = intent.getStringExtra("image")
        var id = intent.getIntExtra("id", 0)

        // Create and show the notification
        if (title != null && description != null && time != null) {
            notificationDialog(context, time.toLong(), title, description,image, id)
        };
    }

    fun notificationDialog(
        context: Context,
        time: Long,
        title: String,
        description: String,
        image: String?,
        id: Int
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "C_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "My Notifications",
                NotificationManager.IMPORTANCE_MAX
            )
            // Configure the notification channel.
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val intent = Intent(context, ViewReminder::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra("title", title)
        intent.putExtra("des", description)
        intent.putExtra("date", time)
        intent.putExtra("time", time)
        intent.putExtra("image", image)
        intent.putExtra("id", id)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(time)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(description)
            .setContentInfo("")
            .setContentIntent(pendingIntent)
        notificationManager.notify(id, notificationBuilder.build())
    }

}