package com.example.remindify

import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams){
    companion object {
        const val TAG = "MyWorker"
    }

    override fun doWork(): Result {
        try {
            Log.d(TAG, "Background task executed:")
            val powerManager = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
            val wakeLock = powerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "MyApp:WakeupLock"
            )
            wakeLock.acquire(10 * 1000L /* 10 seconds */)

            // Release the wake lock
            wakeLock.release()
//             If you want to start a new activity, you can use Intent:
             val intent = Intent(applicationContext, ReminderAlarm::class.java)
             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
             applicationContext.startActivity(intent)

            return Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error in background task", e)
            return Result.failure()
        }
    }
}