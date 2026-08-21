package com.example.forever.di

import com.example.forever.data.repository.NoteRepositoryImpl
import com.example.forever.domain.repository.NoteRepository
import com.example.forever.presentation.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.forever.data.repository.UserPreferencesRepositoryImpl
import com.example.forever.domain.repository.UserPreferencesRepository
import com.example.forever.domain.usecase.*

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
    single<UserPreferencesRepository> { UserPreferencesRepositoryImpl(androidContext()) }

    // USE CASES — заметки
    factory { GetAllNotesUseCase(get()) }
    factory { AddNoteUseCase(get()) }
    factory { UpdateNoteUseCase(get()) }
    factory { DeleteNoteUseCase(get()) }
    factory { GetAttachmentsForNoteUseCase(get()) }
    factory { AddAttachmentUseCase(get()) }
    factory { DeleteAttachmentUseCase(get()) }
    factory { RestoreNoteUseCase(get()) }
    factory { GetRandomNoteUseCase(get()) }

    // USE CASES — настройки
    factory { ObserveOnboardingUseCase(get()) }
    factory { CompleteOnboardingUseCase(get()) }
    factory { ObserveUserNameUseCase(get()) }
    factory { SaveUserNameUseCase(get()) }
    factory { GetRandomNoteUseCase(get()) }

    // 5. VIEWMODEL
    viewModel { MainViewModel(
        getAllNotesUseCase = get(),
        addNoteUseCase = get(),
        updateNoteUseCase = get(),
        deleteNoteUseCase = get(),
        getAttachmentsUseCase = get(),
        addAttachmentUseCase = get(),
        deleteAttachmentUseCase = get(),
        observeOnboardingUseCase = get(),
        completeOnboardingUseCase = get(),
        observeUserNameUseCase = get(),
        saveUserNameUseCase = get(),
        restoreNoteUseCase = get()
        )
    }
}