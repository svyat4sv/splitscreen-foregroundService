package com.example.homework

import android.app.Service
import android.content.Intent
import android.os.Handler

import android.os.IBinder

import android.util.Log
import androidx.annotation.Nullable


class MyBackgroundService : Service() {
    private val handler: Handler = Handler()
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            // Здесь выполняйте вашу фоновую логику
            Log.d("MyBackgroundService", "Фоновая задача выполняется...")

            // Повторите задачу через определенный интервал времени
            handler.postDelayed(this, 10000) // Например, каждые 10 секунд
        }
    }

    override fun onCreate() {
        super.onCreate()
        // Начните выполнение задачи при создании сервиса
        handler.post(runnable)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Верните флаг, чтобы сервис был перезапущен, если он был убит системой
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Остановите выполнение задачи при уничтожении сервиса
        handler.removeCallbacks(runnable)
        Log.d("MyBackgroundService", "Сервис остановлен.")
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}