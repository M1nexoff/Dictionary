// SpeakPresenter.kt

package com.example.dictionary.presenter.screens.speak

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast

class SpeakPresenter(private val view: SpeakContract.View) : SpeakContract.Presenter {

    private val REQ_CODE_SPEECH_INPUT = 100
    private var isUzbek = false
    private val model:SpeakContract.Model = SpeakModel()

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
                val usage = message?.get(0) ?: ""
                val translationCursor = if (isUzbek){model.getFromUzbek(usage) } else model.getFromEnglish(usage)
                if (isUzbek){
                    translationCursor.let {
                        if(it.moveToPosition(0)){
                            val english = it.getString(it.getColumnIndex("english"))
                            val uzbek = it.getString(it.getColumnIndex("uzbek"))
                            view.updateResults(uzbek,english)
                        }
                    }
                }else{
                    translationCursor.let {
                        if(it.moveToPosition(0)){
                            val english = it.getString(it.getColumnIndex("english"))
                            val uzbek = it.getString(it.getColumnIndex("uzbek"))
                            view.updateResults(english,uzbek)
                        }
                    }
                }
            }
        }
    }



}
