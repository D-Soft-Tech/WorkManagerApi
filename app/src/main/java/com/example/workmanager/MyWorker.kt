package com.example.workmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters

const val CHANNEL_ID = "D-Soft-Tech"
const val CHANNEL_NAME = "This is the notification channel of DynamicSoft Technology"
const val NOTIFICATION_ID = 111
const val TASK_TO_DO = "Study NetworkBoundResource and using Sealed classes to load data from the server"
const val TASK_DESCRIPTION = "You have to do and understand this before Sunday, May God help you. Amen"

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    private val notificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        displayNotification(
            TASK_TO_DO,
            TASK_DESCRIPTION
        )
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayNotification(task: String, description: String) {
        // If the android version is running above android Nugget, create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            // Create the notification channel
            notificationManager.createNotificationChannel(channel)
        }

        // Create the notification itself
        val notificationBuilder = Notification.Builder(applicationContext, CHANNEL_ID).apply {
            setContentTitle(task)
            setContentText(description)
            setSmallIcon(R.drawable.work_icon)
        }

        // Use the notification manager to display the notification
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}
