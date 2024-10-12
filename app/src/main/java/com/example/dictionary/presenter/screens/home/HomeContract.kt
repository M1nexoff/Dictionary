package com.example.dictionary.presenter.screens.home

import android.content.Intent
import android.database.Cursor
import com.example.dictionary.data.model.Dictionary

interface HomeContract {
    interface Model {
        fun getFromUzbek(key: String, callback: (Dictionary) -> Unit)
        fun getFromEnglish(key: String, callback: (Dictionary) -> Unit)
        fun getFromUzbek1(key: String, callback: (Cursor) -> Unit)
        fun getFromEnglish1(key: String, callback: (Cursor) -> Unit)
    }

    interface View {
        fun message(s: String)
        fun promptSpeechInput(isUzbek:Boolean,REQ_CODE_SPEECH_INPUT:Int)
    }

    interface Presenter {
        fun onEnglishButtonClicked()
        fun onUzbekButtonClicked()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
//        fun translate(isUzbek: Boolean, data: String)
    }
}