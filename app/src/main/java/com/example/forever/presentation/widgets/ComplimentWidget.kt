package com.example.forever.presentation.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import com.example.forever.MainActivity
import com.example.forever.R
import com.example.forever.domain.usecase.GetRandomNoteUseCase
import kotlinx.coroutines.runBlocking
import org.koin.core.context.GlobalContext

/**
 * Implementation of App Widget functionality.
 */
class ComplimentWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Случайный комплимент через use case — переиспользуем домен!
    val useCase = GlobalContext.get().get<GetRandomNoteUseCase>()
    val note = runBlocking { useCase() }

    val views = RemoteViews(context.packageName, R.layout.compliment_widget)
    views.setTextViewText(
        R.id.widgetText,
        note?.text ?: "Добавь первый комплимент 💗"
    )

    val heartColor = ContextCompat.getColor(context, R.color.pink_heart)
    views.setInt(R.id.heartTop, "setColorFilter", heartColor)
    views.setInt(R.id.heartLeft, "setColorFilter", heartColor)
    views.setInt(R.id.heartRight, "setColorFilter", heartColor)

    // Тап открывает приложение
    val intent = Intent(context, MainActivity::class.java)
    val pending = PendingIntent.getActivity(
        context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    views.setOnClickPendingIntent(R.id.widgetRoot, pending)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}