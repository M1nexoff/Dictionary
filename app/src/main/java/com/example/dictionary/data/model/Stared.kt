package com.example.dictionary.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stared")
data class Stared(
    @PrimaryKey val id: Long,
    var isStared: Int = 0
)