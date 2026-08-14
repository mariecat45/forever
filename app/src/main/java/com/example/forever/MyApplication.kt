package com.example.forever

import android.app.Application
import com.example.forever.di.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Запускаем Koin
        startKoin {
            androidContext(this@MyApplication) // Передаем контекст приложения
            modules(koinModule) // Подключаем наш файл с "рецептами"
        }
    }
}