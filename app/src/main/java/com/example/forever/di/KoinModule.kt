package com.example.forever.di

import com.example.forever.data.repository.NoteRepositoryImpl
import com.example.forever.data.repository.UserPreferencesRepository
import com.example.forever.domain.repository.NoteRepository
import com.example.forever.presentation.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel // Этот импорт нужен для создания ViewModel

val koinModule = module {
    // 1. БАЗА ДАННЫХ (Синглтон - создается один раз)
    single {
        androidx.room.Room.databaseBuilder(
            androidContext(), // ← Теперь androidContext() работает!
            com.example.forever.data.source.database.AppDatabase::class.java,
            "my_notes_database"
        ).build()
    }

    // 2. DAO
    single { get<com.example.forever.data.source.database.AppDatabase>().noteDao() }
    single { get<com.example.forever.data.source.database.AppDatabase>().attachmentDao() }

    // 3. РЕПОЗИТОРИИ
    single<NoteRepository> {
        NoteRepositoryImpl(
            noteDao = get(),
            attachmentDao = get()  // ← Koin найдет AttachmentDao из пункта 2
        )
    }

    // 4. РЕПОЗИТОРИЙ НАСТРОЕК
    single { UserPreferencesRepository(androidContext()) }

    // 5. VIEWMODEL
    viewModel { MainViewModel(
            repository = get(),
            userPreferencesRepository = get()
        )
    }
}