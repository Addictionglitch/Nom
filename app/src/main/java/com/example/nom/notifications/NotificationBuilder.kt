package com.example.nom.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.example.nom.MainActivity
import com.example.nom.R
import com.example.nom.core.data.local.SecurePreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationBuilder @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val notificationMessages = listOf(
        "Spotted something green nearby?",
        "Your Spirit is curious about today's discoveries",
        "A new plant is waiting to be found!",
        "The world is full of undiscovered species",
        "Your Spirit wonders what's growing outside"
    )

    companion object {
        const val CHANNEL_ID = "spirit_updates"
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Spirit Updates"
            val descriptionText = "Notifications about your Spirit's status and discoveries"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun buildCuriosityNotification(securePreferences: SecurePreferences) {
        fun canShowNotification(): Boolean {
            val lastNotificationTimestamp = securePreferences.getLastNotificationTimestamp()
            val currentTime = System.currentTimeMillis()
            val twelveHoursInMillis = TimeUnit.HOURS.toMillis(12)
            return currentTime - lastNotificationTimestamp > twelveHoursInMillis
        }

        if (!canShowNotification()) {
            Timber.d("Notification skipped, already shown recently.")
            return
        }

        createNotificationChannel()

        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            "nom://spirit/home".toUri(),
            context,
            MainActivity::class.java
        )

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, deepLinkIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with a real icon
            .setContentTitle("Nom")
            .setContentText(notificationMessages.random())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
            securePreferences.setLastNotificationTimestamp(System.currentTimeMillis())
            Timber.d("Curiosity notification sent.")
        }
    }
}
