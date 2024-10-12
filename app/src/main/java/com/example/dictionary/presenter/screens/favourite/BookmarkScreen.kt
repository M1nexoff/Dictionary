package com.example.dictionary.presenter.screens.favourite

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.ScreenBookmarkBinding
import com.example.dictionary.presenter.adapters.FavouriteAdapter
import com.example.dictionary.presenter.screens.definition.DefinitionScreen

class BookmarkScreen: Fragment(R.layout.screen_bookmark) {
    private val binding: ScreenBookmarkBinding by viewBinding(ScreenBookmarkBinding::bind)
    private lateinit var adapter: FavouriteAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FavouriteAdapter(requireContext())
        adapter.onClick = {data ->
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container,DefinitionScreen().also {
                    it.arguments = bundleOf("data" to data)
                })
                .commit()
        }
        adapter.onUpdate = {
            binding.emptyBox.isInvisible = !it
            binding.emptyText.isInvisible = !it
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