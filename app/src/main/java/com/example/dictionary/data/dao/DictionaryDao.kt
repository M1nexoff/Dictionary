package com.example.dictionary.data.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionary.data.model.Stared

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary WHERE english LIKE :key || '%'")
    fun getFromEnglish(key: String): Cursor

    @Query("SELECT * FROM dictionary WHERE uzbek LIKE :key || '%'")
    fun getFromUzbek(key: String): Cursor
    @Query("SELECT dictionary.* FROM dictionary, stared WHERE english LIKE :key || '%' and dictionary.id == stared.id and stared.isStared = true")
    fun getStaredFromEnglish(key: String): Cursor

    @Query("SELECT dictionary.* FROM dictionary, stared WHERE uzbek LIKE :key || '%' and dictionary.id == stared.id and stared.isStared = true")
    fun getStaredFromUzbek(key: String): Cursor

    @Query("SELECT * FROM dictionary")
    fun getAll():Cursor

    @Query("SELECT * FROM stared where id = :id")
    fun getStared(id: Long): Stared

    @Query("SELECT dictionary.* FROM stared,dictionary where stared.id = dictionary.id and stared.isStared = true")
    fun getAllStared():Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStared(data:Stared):Long
}