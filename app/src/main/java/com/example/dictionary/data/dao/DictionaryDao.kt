package com.example.dictionary.data.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.data.model.Stared

@Dao
interface DictionaryDao {

    @Query("SELECT * FROM dictionary WHERE english LIKE :key || '%'")
    fun getFromEnglish(key: String): Cursor

    @Query("SELECT * FROM dictionary WHERE uzbek LIKE :key || '%'")
    fun getFromUzbek(key: String): Cursor

    @Query("SELECT * FROM dictionary WHERE english LIKE :key || '%' AND isStared = 1")
    fun getStaredFromEnglish(key: String): Cursor

    @Query("SELECT * FROM dictionary WHERE uzbek LIKE :key || '%' AND isStared = 1")
    fun getStaredFromUzbek(key: String): Cursor

    @Query("SELECT * FROM dictionary")
    fun getAll(): Cursor

    @Query("SELECT * FROM dictionary WHERE id = :id")
    fun getWordById(id: Long): Dictionary

    @Query("SELECT * FROM dictionary WHERE isStared = 1")
    fun getAllStared(): Cursor

    @Query("SELECT * FROM dictionary WHERE uzbek LIKE :key || '%' LIMIT 1")
    fun getFromUzbekOne(key: String): Dictionary

    @Query("SELECT * FROM dictionary WHERE english LIKE :key || '%' LIMIT 1")
    fun getFromEnglishOne(key: String): Dictionary

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(data: Dictionary)

    // Update favorite status in the Dictionary table without changing the method signature
    @Query("UPDATE dictionary SET isStared = :isStared WHERE id = :id")
    fun insertStared(id: Long,isStared: Int): Int
    @Query("UPDATE dictionary SET lastAccessed = :timestamp WHERE id = :id")
    fun updateLastAccessed(id: Long, timestamp: Long): Int

    @Query("SELECT * FROM dictionary WHERE lastAccessed IS NOT NULL ORDER BY lastAccessed DESC")
    fun getRecentHistory(): Cursor

    @Query("UPDATE dictionary SET lastAccessed = NULL WHERE id = :id")
    fun clearHistoryForWord(id: Long): Int

    // Clear all history
    @Query("UPDATE dictionary SET lastAccessed = NULL WHERE lastAccessed IS NOT NULL")
    fun clearAllHistory(): Int
}

/*

    2     4     8     16

   32    64    128   256

   512   1024  2048  4096

   8192  16384 32768 65536

   */
