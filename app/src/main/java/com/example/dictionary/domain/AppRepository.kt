package com.example.dictionary.domain

import android.database.Cursor

interface AppRepository {
    fun getFromEnglish(key: String): Cursor
    fun getAll():Cursor
}