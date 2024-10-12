package com.example.dictionary.presenter.screens.home

import android.database.Cursor
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.domain.AppRepository
import com.example.dictionary.domain.AppRepositoryImpl

class HomeModel : HomeContract.Model {
    private val appRepository: AppRepository = AppRepositoryImpl.getAppRepository()

    override fun getFromUzbek(key: String, callback: (Dictionary) -> Unit) {
        appRepository.getFromUzbekOne(key) { dictionary ->
            callback(dictionary)
        }
    }

    override fun getFromEnglish(key: String, callback: (Dictionary) -> Unit) {
        appRepository.getFromEnglishOne(key) { dictionary ->
            callback(dictionary)
        }
    }

    override fun getFromUzbek1(key: String, callback: (Cursor) -> Unit) {
        appRepository.getFromUzbek(key) { cursor ->
            callback(cursor)
        }
    }

    override fun getFromEnglish1(key: String, callback: (Cursor) -> Unit) {
        appRepository.getFromEnglish(key) { cursor ->
            callback(cursor)
        }
    }
}
