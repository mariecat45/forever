package com.example.forever.presentation.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.forever.MainActivity
import com.example.forever.R
import com.example.forever.domain.usecase.GetRandomNoteUseCase
import kotlinx.coroutines.runBlocking
import org.koin.core.context.GlobalContext

class ComplimentWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (widgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, widgetId)
        }
    }

    private fun updateWidget(context: Context, manager: AppWidgetManager, widgetId: Int) {
        val useCase = GlobalContext.get().get<GetRandomNoteUseCase>()
        val note = runBlocking { useCase() }

        val views = RemoteViews(context.packageName, R.layout.widget_compliment).apply {
            setTextViewText(
                R.id.widgetText,
                note?.text ?: "Добавь первую запись"
            )
        }

        // Тап по виджету открывает приложение
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.widgetRoot, pendingIntent)

        manager.updateAppWidget(widgetId, views)
    }
}