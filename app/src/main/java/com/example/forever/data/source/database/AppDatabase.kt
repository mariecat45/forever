package com.example.forever.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forever.data.source.local.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false // Отключаем экспорт схемы, чтобы не было лишних папок при разработке
)
abstract class AppDatabase : RoomDatabase() {

    // Связываем БД с DAO
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Метод для получения единственного экземпляра БД (Singleton)
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database" // Имя файла базы данных на устройстве
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}