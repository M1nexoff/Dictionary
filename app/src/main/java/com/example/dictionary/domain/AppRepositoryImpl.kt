package com.example.dictionary.domain

import android.database.Cursor
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.data.model.Stared
import com.example.dictionary.data.source.AppDatabase
import com.example.dictionary.runOnMainThread
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppRepositoryImpl : AppRepository{
    companion object {
        private lateinit var instance: AppRepository
        fun init() {
            if (!(::instance.isInitialized)) instance = AppRepositoryImpl()
        }

        fun getAppRepository(): AppRepository = instance
    }
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val dictionaryDao = AppDatabase.init().dictionaryDao()


    override fun getFromEnglish(key: String, callBack: (Cursor) -> Unit) {
        executor.execute {
            val cursor = dictionaryDao.getFromEnglish(key)
            runOnMainThread {
                callBack(cursor)
            }
        }
    }

    override fun getFromUzbek(key: String, callBack: (Cursor) -> Unit) {
        executor.execute {
            val cursor = dictionaryDao.getFromUzbek(key)
            runOnMainThread {
                callBack(cursor)
            }
        }
    }

    override fun getAll(callBack: (Cursor) -> Unit) {
        executor.execute {
            val cursor = dictionaryDao.getAll()
            runOnMainThread {
                callBack(cursor)
            }
        }
    }

    override fun getStared(id: Long, callBack: (Stared) -> Unit) {
        executor.execute {
            val stared = dictionaryDao.getWordById(id)
            runOnMainThread {
                callBack(Stared(stared.id,stared.isStared))
            }
        }
    }

    override fun getAllStared(callBack: (Cursor) -> Unit) {
        executor.execute {
            val cursor = dictionaryDao.getAllStared()
            runOnMainThread {
                callBack(cursor)
            }
        }
    }

    override fun insertStared(data: Stared, callBack: (Long) -> Unit) {
        executor.execute {
            val id = dictionaryDao.insertStared(data.id,data.isStared)
//            val id2 = dictionaryDao.setStared(data)
            runOnMainThread {
                callBack(id.toLong())
            }
        }
    }

    override fun getStaredFromEnglish(key: String, callBack: (Cursor) -> Unit) {
        executor.execute {
            val cursor = dictionaryDao.getStaredFromEnglish(key)
            runOnMainThread {
                callBack(cursor)
            }
        }
    }


    override fun getStaredFromUzbek(key: String, callBack: (Cursor) -> Unit) {
        executor.execute {
            val cursor = dictionaryDao.getStaredFromUzbek(key)
            runOnMainThread {
                callBack(cursor)
            }
        }
    }

    override fun getFromUzbekOne(key: String, callBack: (Dictionary) -> Unit) {
        executor.execute {
            val dictionary = dictionaryDao.getFromUzbekOne(key)
            runOnMainThread {
                callBack(dictionary)
            }
        }
    }

    override fun getFromEnglishOne(key: String, callBack: (Dictionary) -> Unit) {
        executor.execute {
            val dictionary = dictionaryDao.getFromEnglishOne(key)
            runOnMainThread {
                callBack(dictionary)
            }
        }
    }

    override fun getWordById(id: Long, callBack: (Dictionary) -> Unit) {
        executor.execute {
            val dictionary = dictionaryDao.getWordById(id)
            runOnMainThread {
                callBack(dictionary)
            }
        }
    }
    override fun updateLastAccessed(id: Long, timestamp: Long, callback: (Int) -> Unit) {
        executor.execute {
            val result = dictionaryDao.updateLastAccessed(id, timestamp)
            runOnMainThread {
                callback(result)
            }
        }
    }

    override fun getRecentHistory(callback: (Cursor) -> Unit) {
        executor.execute {
            val cursor = dictionaryDao.getRecentHistory()
            runOnMainThread {
                callback(cursor)
            }
        }
    }
    override fun clearHistoryForWord(id: Long, callback: (Int) -> Unit) {
        executor.execute {
            val result = dictionaryDao.clearHistoryForWord(id)
            runOnMainThread {
                callback(result)
            }
        }
    }

    override fun clearAllHistory(callback: (Int) -> Unit) {
        executor.execute {
            val result = dictionaryDao.clearAllHistory()
            runOnMainThread {
                callback(result)
            }
        }
    }
}