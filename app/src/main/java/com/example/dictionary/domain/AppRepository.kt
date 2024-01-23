package com.example.dictionary.domain

import android.database.Cursor
import androidx.room.Query
import com.example.dictionary.data.model.Stared

interface AppRepository {
    fun getFromEnglish(key: String): Cursor
    fun getFromUzbek(key: String): Cursor
    fun getAll():Cursor
    fun getStared(id: Long): Stared
    fun getAllStared():Cursor
    fun insertStared(data:Stared):Long
    fun getStaredFromEnglish(key: String): Cursor
    fun getStaredFromUzbek(key: String): Cursor
}