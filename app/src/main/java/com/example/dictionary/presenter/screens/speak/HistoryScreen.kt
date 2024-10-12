// SpeakScreen.kt

package com.example.dictionary.presenter.screens.speak

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.PageSpeakBinding
import com.example.dictionary.databinding.ScreenHistoryBinding
import com.example.dictionary.domain.AppRepository
import com.example.dictionary.domain.AppRepositoryImpl
import com.example.dictionary.presenter.adapters.FavouriteAdapter
import com.example.dictionary.presenter.adapters.HistoryAdapter
import com.example.dictionary.presenter.screens.definition.DefinitionScreen
import com.example.dictionary.presenter.screens.home.HomePresenter

class HistoryScreen : Fragment(R.layout.screen_history) {

    private val binding: ScreenHistoryBinding by viewBinding(ScreenHistoryBinding::bind)
    private val appRepository = AppRepositoryImpl.getAppRepository()
    private lateinit var adapter: HistoryAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HistoryAdapter(requireContext())
        adapter.onClick = {data->
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, DefinitionScreen().also {
                    it.arguments = bundleOf("data" to data)
                })
                .commit()
        }
        adapter.onUpdate = {
            binding.emptyBox.isInvisible = !it
            binding.emptyText.isInvisible = !it
            binding.clearAllHistory.isInvisible = it
        }
        binding.clearAllHistory.setOnClickListener {
            appRepository.clearAllHistory {
                adapter.isUzbek = adapter.isUzbek
            }
        }
        binding.recycler.adapter = adapter
        binding.language.setOnClickListener {
            if(adapter.isUzbek){
                binding.language.setImageResource(R.drawable.en)
                adapter.isUzbek = false
            }else {
                binding.language.setImageResource(R.drawable.uz)
                adapter.isUzbek = true
            }
        }
    }
    override fun onResume() {
        super.onResume()
        adapter.isUzbek = adapter.isUzbek
    }

}
