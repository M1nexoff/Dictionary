// SpeakContract.kt

package com.example.dictionary.presenter.screens.speak

import android.content.Intent
import android.database.Cursor

interface SpeakContract {
    interface Model {
        fun getFromUzbek(key: String): Cursor
        fun getFromEnglish(key: String): Cursor
    }

    interface View {
        fun updateResults(s: String,translation: String)
        fun promptSpeechInput(isUzbek:Boolean,REQ_CODE_SPEECH_INPUT:Int)
    }

    interface Presenter {
        fun onEnglishButtonClicked()
        fun onUzbekButtonClicked()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }
}
