package com.example.dictionary.domain

import android.database.Cursor
import com.example.dictionary.data.model.Stared
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

    override fun getStared(id: Long): Stared {
        return wordDao.getStared(id)
    }

    override fun getAllStared(): Cursor {
        return wordDao.getAllStared()
    }

    override fun insertStared(data: Stared): Long {
        return wordDao.insertStared(data)
    }

    override fun getStaredFromEnglish(key: String): Cursor {
        return wordDao.getStaredFromEnglish(key)
    }

    override fun getStaredFromUzbek(key: String): Cursor {
        return wordDao.getStaredFromUzbek(key)
    }
}