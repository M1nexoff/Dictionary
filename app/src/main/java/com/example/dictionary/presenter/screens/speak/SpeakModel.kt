package com.example.dictionary.presenter.screens.speak

import android.database.Cursor
import com.example.dictionary.domain.AppRepository
import com.example.dictionary.domain.AppRepositoryImpl

class SpeakModel: SpeakContract.Model {
    val appRepository: AppRepository = AppRepositoryImpl.getAppRepository()
    override fun getFromUzbek(key: String): Cursor {
        return appRepository.getFromUzbek(key)
    }

    override fun getFromEnglish(key: String): Cursor {
        return appRepository.getFromEnglish(key)
    }
}