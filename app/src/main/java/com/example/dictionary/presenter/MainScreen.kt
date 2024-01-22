package com.example.dictionary.presenter

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.ScreenMainBinding
import com.example.dictionary.presenter.adapters.ScreensAdapter
import com.example.dictionary.presenter.screens.favourite.FavouriteScreen
import com.example.dictionary.presenter.screens.speak.SpeakScreen
import com.example.dictionary.presenter.screens.translate.TranslateScreen

class MainScreen() : Fragment(R.layout.screen_main) {
    private val binding: ScreenMainBinding by viewBinding(ScreenMainBinding::bind)
    private var adapter: ScreensAdapter? = null
    private val firstPage by lazy { TranslateScreen() }
    private val secondPage by lazy { SpeakScreen() }
    private val thirdPage by lazy { FavouriteScreen() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ScreensAdapter(this, firstPage, secondPage, thirdPage)
        binding.pager.adapter = adapter

        binding.pager.isUserInputEnabled = false

        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigationView.menu[position].isChecked = true
            }
        })

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.translate -> binding.pager.currentItem = 0
                R.id.speak -> binding.pager.currentItem = 1
                R.id.favourite -> binding.pager.currentItem = 2
            }
            return@setOnItemSelectedListener true
        }
    }
}