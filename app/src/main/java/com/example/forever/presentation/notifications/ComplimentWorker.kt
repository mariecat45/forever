package com.example.forever.presentation.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.forever.domain.usecase.GetRandomNoteUseCase
import org.koin.core.context.GlobalContext

class ComplimentWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val useCase = GlobalContext.get().get<GetRandomNoteUseCase>()
        val note = useCase() ?: return Result.success()

        NotificationHelper.showCompliment(
            applicationContext,
            note.text ?: "Ты прекрасен! 💗"
        )
        return Result.success()
    }
}
