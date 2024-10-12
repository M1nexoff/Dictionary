// SpeakPresenter.kt

package com.example.dictionary.presenter.screens.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import com.example.dictionary.domain.AppRepository
import com.example.dictionary.domain.AppRepositoryImpl

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    val appRepository : AppRepository = AppRepositoryImpl.getAppRepository()
    private val REQ_CODE_SPEECH_INPUT = 100
    private var isUzbek = false
    private val model: HomeContract.Model = HomeModel()

    override fun onEnglishButtonClicked() {
        isUzbek = false
        view.promptSpeechInput(isUzbek,REQ_CODE_SPEECH_INPUT)
    }

    override fun onUzbekButtonClicked() {
        isUzbek = true
        view.promptSpeechInput(isUzbek,REQ_CODE_SPEECH_INPUT)
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val message = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                view.message((message?.get(0)) ?: "")
            }
        }
    }

//    @SuppressLint("Range")
//    override fun translate(isUzbek: Boolean, data: String) {
//        val translation = if (isUzbek){model.getFromUzbek(data) } else model.getFromEnglish(data)
//        if (translation.isNotEmpty()){
//            view.updateResults(data, translation)
//        }else if (isUzbek){
//            view.updateResults(data,"Hech narsa topilmadi!")
//        }else{
//            view.updateResults(data,"Nothing found!")
//        }
//    }
}