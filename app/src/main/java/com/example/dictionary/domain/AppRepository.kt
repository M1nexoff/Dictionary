package com.example.dictionary.domain

import android.database.Cursor
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.data.model.Stared

interface AppRepository {
    fun getFromEnglish(key: String, callBack: (Cursor) -> Unit)
    fun getFromUzbek(key: String, callBack: (Cursor) -> Unit)
    fun getAll(callBack: (Cursor) -> Unit)
    fun getStared(id: Long, callBack: (Stared) -> Unit)
    fun getAllStared(callBack: (Cursor) -> Unit)
    fun insertStared(data: Stared, callBack: (Long) -> Unit)
    fun getStaredFromEnglish(key: String, callBack: (Cursor) -> Unit)
    fun getStaredFromUzbek(key: String, callBack: (Cursor) -> Unit)
    fun getFromUzbekOne(key: String, callBack: (Dictionary) -> Unit)
    fun getFromEnglishOne(key: String, callBack: (Dictionary) -> Unit)
    fun getWordById(id: Long, callBack: (Dictionary) -> Unit)
    fun updateLastAccessed(id: Long, timestamp: Long, callback: (Int) -> Unit)
    fun getRecentHistory(callback: (Cursor) -> Unit)
    fun clearHistoryForWord(id: Long, callback: (Int) -> Unit)
    fun clearAllHistory(callback: (Int) -> Unit)
}
