package com.example.dictionary.presenter.screens.definition

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.data.model.Stared
import com.example.dictionary.databinding.ScreenInfoBinding
import com.example.dictionary.domain.AppRepositoryImpl
import java.util.Locale


class DefinitionScreen(): Fragment(R.layout.screen_info),TextToSpeech.OnInitListener {
    private val binding: ScreenInfoBinding by viewBinding(ScreenInfoBinding::bind)
    private val appRepository = AppRepositoryImpl.getAppRepository()
    private var isStared = 0
    private val tts: TextToSpeech by lazy { TextToSpeech(context, this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = requireArguments().getSerializable("data") as? Dictionary?: Dictionary(0,"","","","","",0)
        appRepository.updateLastAccessed(data.id,System.currentTimeMillis()){

        }
        isStared = data.isStared
        binding.english.text = data.english
        binding.transcript.text = data.transcript
        binding.type.text = data.type
        binding.uzbek.text = data.uzbek
        updateBookmark(data.id)
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.bookmark.setOnClickListener {
            appRepository.insertStared(Stared(data.id,if (isStared==0)1 else 0)){
                updateBookmark(data.id)
            }
        }
        binding.copyEnglish.setOnClickListener {
            requireContext().copyToClipboard(data.english)
        }
        binding.copyTranscript.setOnClickListener {
            requireContext().copyToClipboard(data.transcript)
        }
        binding.copyUzbek.setOnClickListener {
            requireContext().copyToClipboard(data.uzbek)
        }
        binding.copyAll.setOnClickListener {
            val copyText = """
                English: ${data.english}
                Type: ${data.type}
                Transcript: ${data.transcript}
                Uzbek: ${data.uzbek}
            """.trimIndent()
            requireContext().copyToClipboard(copyText)
        }
        binding.share.setOnClickListener {
            val copyText = """
                English: ${data.english}
                Type: ${data.type}
                Transcript: ${data.transcript}
                Uzbek: ${data.uzbek}
            """.trimIndent()
            val i = Intent(Intent.ACTION_SEND)
            i.setType("text/plain")
            i.putExtra(Intent.EXTRA_TEXT, copyText)
            startActivity(i)
        }
        binding.voice.setOnClickListener {
            speakOutUS(data.english)
        }
        binding.voiceUzbek.setOnClickListener {
            speakOutUZ(data.uzbek)
        }

    }
    private fun updateBookmark(id:Long){
        appRepository.getWordById(id){
            if (isStared == 1){
                binding.bookmark.setImageResource(R.drawable.bookmark)
            }else{
                binding.bookmark.setImageResource(R.drawable.bookmark_unused)
            }
            isStared = it.isStared
        }
    }
    fun Context.copyToClipboard(text: CharSequence){
        val clipboard = ContextCompat.getSystemService(this,ClipboardManager::class.java)
        clipboard?.setPrimaryClip(ClipData.newPlainText("",text))
        Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Toast.makeText(context,"Fail", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context,"Fail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOutUS(text: String) {
        tts.setLanguage(Locale.US)
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
    private fun speakOutUZ(text: String) {
        tts.setLanguage(Locale("uz", "UZ"))
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tts.stop()
        tts.shutdown()
    }
}