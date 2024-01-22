// SpeakScreen.kt

package com.example.dictionary.presenter.screens.speak

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.PageSpeakBinding

class SpeakScreen : Fragment(R.layout.page_speak), SpeakContract.View {

    private val binding: PageSpeakBinding by viewBinding(PageSpeakBinding::bind)
    private val presenter: SpeakContract.Presenter by lazy { SpeakPresenter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.english.setOnClickListener {
            presenter.onEnglishButtonClicked()
        }

        binding.uzbek.setOnClickListener {
            presenter.onUzbekButtonClicked()
        }
    }

    override fun updateResults(s: String,translation: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show()
        binding.usage.text = s
        binding.translate.text = translation
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun promptSpeechInput(isUzbek:Boolean,REQ_CODE_SPEECH_INPUT:Int) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        if (isUzbek) {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "uz-UZ")
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Gapiring")
        } else {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-GB")
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something")
        }
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(), "Sorry! Your device doesn\\'t support speech input",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
