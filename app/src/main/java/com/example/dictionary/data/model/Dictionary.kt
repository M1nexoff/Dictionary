package com.example.dictionary.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "dictionary")
data class Dictionary(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val english: String,

    val type: String,

    val transcript: String,

    val uzbek: String,

    val countable: String,

    val isStared: Int,

    val lastAccessed: Long? = null

): Serializable