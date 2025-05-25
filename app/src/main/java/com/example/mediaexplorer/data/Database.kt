package com.example.mediaexplorer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mediaexplorer.data.dao.CategoryDao
import com.example.mediaexplorer.data.dao.ContentDao
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.entity.Content

@Database(entities = [Category::class, Content::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun contentDao(): ContentDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build().also { Instance = it }
            }
        }
    }
}