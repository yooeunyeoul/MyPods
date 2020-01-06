package com.dongeul_dev.pods

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.orhanobut.logger.Logger

class PodService : Service() {
    
    companion object {
        private var notification: Notification? = null

        private val CHANNEL_ID = "ForegroundService Pods"

        fun startService(context: Context, message: String) {
            Intent(context, PodService::class.java).apply {
                putExtra("InputExtra", message)
                ContextCompat.startForegroundService(context, this)
            }
        }

        fun stopService(context: Context) {
            Intent(context, PodService::class.java).apply {
                context.stopService(this)
            }
        }

        fun notifyYo(context:Context) {
            val manager = getSystemService(context,NotificationManager::class.java)
            val notification2 = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("ForeGround Service ")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build()


            manager?.notify(1, notification2)

        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Logger.d("onStartCommand")
        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)


        val notificationBig = RemoteViews(packageName, R.layout.status_big)
        notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("ForeGround Service ")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setCustomContentView(notificationBig)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
        // Stop Self
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Channel Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)

        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }


}