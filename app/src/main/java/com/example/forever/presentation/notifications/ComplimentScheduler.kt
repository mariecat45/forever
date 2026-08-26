package com.example.forever.presentation.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object ComplimentScheduler {

    fun schedule(context: Context) {
        val request = PeriodicWorkRequestBuilder<ComplimentWorker>(
            4, TimeUnit.HOURS   // каждые 4 часа
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "compliment_notifications",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}