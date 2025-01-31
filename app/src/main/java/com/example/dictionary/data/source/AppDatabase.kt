package com.example.dictionary.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionary.data.dao.DictionaryDao
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.data.model.Stared

@Database(entities = [Dictionary::class,Stared::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao


    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context.applicationContext).also { instance = it }
            }
        }
        fun init():AppDatabase{
            return instance!!
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "Data.db"
            ).allowMainThreadQueries()
                .createFromAsset("database/dictionary.db")
                .build()
        }
    }
}

