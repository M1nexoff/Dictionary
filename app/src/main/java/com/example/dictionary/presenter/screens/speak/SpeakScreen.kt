package com.example.dictionary.presenter.screens.speak

import android.app.Activity
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

class SpeakScreen: Fragment(R.layout.page_speak) {
    private val REQ_CODE_SPEECH_INPUT = 100
    private val binding: PageSpeakBinding by viewBinding(PageSpeakBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.setOnClickListener {
            promptSpeechInput()
        }
    }
    fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU")

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something")

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(), "Sorry! Your device doesn\\'t support speech input",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val message = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                binding.text.text = message?.get(0) ?: ""
            }
        }
    }

    fun updateResults(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show()
        binding.text.text = s
    }

}