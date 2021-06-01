package com.example.githubuserapp.utils

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.githubuserapp.R
import com.example.githubuserapp.views.MainActivity
import java.util.*

class Notification: BroadcastReceiver() {
    companion object{
        private const val REMINDER_CODE = 101
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        showNotification(context)
    }

    private fun showNotification(context: Context?){
        val channelId = "reminder_channel"
        val channelName = "reminder_github_user"

        val title = context?.resources?.getString(R.string.reminder_name)
        val message = context?.resources?.getString(R.string.reminder_message)

        val intent= Intent(context, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context)
            .addParentStack(MainActivity::class.java)
            .addNextIntent(intent)
            .getPendingIntent(REMINDER_CODE, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManagerCompat =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mBuilder = NotificationCompat.Builder(context, channelId)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.octocat)
            .setContentTitle(title)
            .setContentText(message)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setSound(alarmSound)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            }
            mBuilder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = mBuilder.build()
        notificationManagerCompat.notify(REMINDER_CODE, notification)
    }

    fun setNotification(context: Context?){
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent: PendingIntent = Intent(context, Notification::class.java).let {
            PendingIntent.getBroadcast(context, REMINDER_CODE, it, 0)
        }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            intent
        )

        Toast.makeText(context, "Notifikasi Dinyalakan", Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context?){
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Notification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, REMINDER_CODE, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, "Notifikasi Dimatikan", Toast.LENGTH_SHORT).show()
    }
}