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
import com.example.dictionary.presenter.screens.favourite.BookmarkScreen
import com.example.dictionary.presenter.screens.home.HomeScreen
import com.example.dictionary.presenter.screens.speak.HistoryScreen

class MainScreen() : Fragment(R.layout.screen_main) {
    private val binding: ScreenMainBinding by viewBinding(ScreenMainBinding::bind)
    private var adapter: ScreensAdapter? = null
    private val firstPage by lazy { BookmarkScreen() }
    private val secondPage by lazy { HomeScreen() }
    private val thirdPage by lazy { HistoryScreen() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ScreensAdapter(this, firstPage, secondPage, thirdPage)
        binding.pager.adapter = adapter
        binding.bottomNavigationView.setSelectedItemId(R.id.home);
        binding.pager.currentItem = 1

        binding.pager.isUserInputEnabled = false

        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigationView.menu[position].isChecked = true
            }
        })

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bookmark -> binding.pager.currentItem = 0
                R.id.home -> binding.pager.currentItem = 1
                R.id.history -> binding.pager.currentItem = 2
            }
            return@setOnItemSelectedListener true
        }

    }
}