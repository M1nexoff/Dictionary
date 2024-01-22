package com.example.dictionary.data.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary WHERE english LIKE :key || '%'")
    fun getFromEnglish(key: String): Cursor

    @Query("SELECT * FROM dictionary WHERE uzbek LIKE :key || '%'")
    fun getFromUzbek(key: String): Cursor

    @Query("SELECT * FROM dictionary")
    fun getAll():Cursor
}