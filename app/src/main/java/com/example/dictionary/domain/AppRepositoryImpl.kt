package com.example.dictionary.domain

import android.database.Cursor
import com.example.dictionary.data.source.AppDatabase

class AppRepositoryImpl : AppRepository{
    companion object {
        private lateinit var instance: AppRepository

        fun init() {
            if (!(::instance.isInitialized)) instance = AppRepositoryImpl()
        }

        fun getAppRepository(): AppRepository = instance
    }

    private val wordDao = AppDatabase.init().dictionaryDao()

    override fun getFromEnglish(key: String): Cursor {
        return wordDao.getFromEnglish(key)
    }

    override fun getFromUzbek(key: String): Cursor {
        return wordDao.getFromUzbek(key)
    }

    override fun getAll(): Cursor {
        return wordDao.getAll()
    }

}