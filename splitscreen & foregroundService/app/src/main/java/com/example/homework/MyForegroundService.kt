package com.example.homework


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationCompat.PRIORITY_DEFAULT

class MyForegroundService : Service() {
    val soundUri = Uri.parse("android.resource://" + "com.example.homework" + "/" + R.raw.music)

    private val NOTIFICATION_ID = 123
    private val CHANNEL_ID = "ForegroundServiceChannel"
    private val SOUND_ON_ACTION = "SOUND_ON"
    private val SOUND_OFF_ACTION = "SOUND_OFF"

    private var mediaPlayer: MediaPlayer? = null
    private var isSoundOn = true

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer?.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        handleNotificationActions(intent)
        mediaPlayer!!.start()

        val notification = buildNotification()
        startForeground(NOTIFICATION_ID, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        stopForeground(true)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = CHANNEL_ID
            val channelName = "Foreground Service Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)

            // Настройка звука уведомления через канал
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            channel.setSound(soundUri, audioAttributes)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        val soundActionTitle = if (isSoundOn) "Выключить звук" else "Включить звук"
        val soundActionIntent = if (isSoundOn) SOUND_OFF_ACTION else SOUND_ON_ACTION

        val soundAction = NotificationCompat.Action.Builder(
            android.R.drawable.ic_media_pause, soundActionTitle,
            getPendingIntentForAction(soundActionIntent)
        ).build()

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Пример Foreground Service")
            .setContentText("Служба выполняется в переднем плане")
            .setSmallIcon(R.drawable.ic_notification)
            .addAction(soundAction)
            .setPriority(PRIORITY_DEFAULT)
            .build()
    }

    private fun getPendingIntentForAction(action: String): PendingIntent {
        val intent = Intent(this, MyForegroundService::class.java)
        intent.action = action
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_MUTABLE)
    }

    private fun handleNotificationActions(intent: Intent?) {
        when (intent?.action) {
            SOUND_ON_ACTION -> {
                isSoundOn = true
                mediaPlayer?.start()
            }
            SOUND_OFF_ACTION -> {
                isSoundOn = false
                mediaPlayer?.pause()
            }
        }
    }
}