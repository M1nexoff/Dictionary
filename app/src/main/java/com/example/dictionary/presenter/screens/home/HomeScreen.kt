package com.example.dictionary.presenter.screens.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.presenter.adapters.DictionaryAdapter
import com.example.dictionary.R
import com.example.dictionary.databinding.ScreenHomeBinding
import com.example.dictionary.presenter.screens.definition.DefinitionScreen

class HomeScreen: Fragment(R.layout.screen_home) , HomeContract.View{
    private val presenter: HomeContract.Presenter by lazy { HomePresenter(this) }
    private val binding: ScreenHomeBinding by viewBinding(ScreenHomeBinding::bind)
    private lateinit var adapter: DictionaryAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DictionaryAdapter(requireContext())
        adapter.onClick = {data->
             requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, DefinitionScreen().also {
                    it.arguments = bundleOf("data" to data)
                })
                .commit()
        }
        adapter.onUpdate = {
            binding.notFound.isInvisible = !it
        }
        binding.recycler.adapter = adapter
        binding.language.setOnClickListener {
            if(adapter.isUzbek){
                binding.language.setImageResource(R.drawable.en)
                adapter.isUzbek = false
                adapter.search = adapter.search
            }else {
                binding.language.setImageResource(R.drawable.uz)
                adapter.isUzbek = true
                adapter.search = adapter.search
            }
        }
        binding.micIcon.setOnClickListener {
            if (adapter.isUzbek) {
                presenter.onUzbekButtonClicked()
            }else{
                presenter.onEnglishButtonClicked()
            }
        }
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            var text = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    text = s.toString()
                }
                adapter.search = text
            }
        })

    }
    override fun onResume() {
        super.onResume()
        adapter.search = adapter.search
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun message(s: String) {
        binding.searchInput.setText(s)
    }
    override fun promptSpeechInput(isUzbek:Boolean,REQ_CODE_SPEECH_INPUT:Int) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        if (isUzbek) {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "uz-UZ")
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Gapiring")
        } else {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-GB")
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say your word")
        }
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (e: ActivityNotFoundException) {
            val toastMessage = if (isUzbek) {
                "Sizning qurilmangiz og'zaki kiritishni qo'llab quvvatlamaydi"
            }else{
                "Sorry! Your device doesn\\'t support speech input"
            }
            Toast.makeText(
                requireContext(), toastMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}