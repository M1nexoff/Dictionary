package com.example.dictionary.app

import android.app.Application
import com.example.dictionary.data.source.AppDatabase
import com.example.dictionary.domain.AppRepository
import com.example.dictionary.domain.AppRepositoryImpl

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
        AppRepositoryImpl.init()
    }
}