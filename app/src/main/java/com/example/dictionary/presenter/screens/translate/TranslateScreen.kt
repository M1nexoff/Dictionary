package com.example.dictionary.presenter.screens.translate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.presenter.adapters.DictionaryAdapter
import com.example.dictionary.R
import com.example.dictionary.databinding.PageTranslateBinding

class TranslateScreen: Fragment(R.layout.page_translate) {
    private val binding: PageTranslateBinding by viewBinding(PageTranslateBinding::bind)
    private lateinit var adapter: DictionaryAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DictionaryAdapter()
        binding.recycler.adapter = adapter
        binding.language.setOnClickListener {
            if(adapter.isUzbek){
                (it as TextView).text = "Eng"
                adapter.isUzbek = false
                adapter.search = adapter.search
            }else {
                (it as TextView).text = "Uzb"
                adapter.isUzbek = true
                adapter.search = adapter.search
            }
        }
        binding.search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            var text = ""
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    text = newText
                }
                adapter.search = text
                return true
            }
        })
        val closeButton = binding.search.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            binding.search.setQuery(null, false)
            binding.search.clearFocus()
        }
    }
}