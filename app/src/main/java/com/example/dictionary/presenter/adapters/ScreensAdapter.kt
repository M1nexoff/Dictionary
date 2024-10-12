package com.example.dictionary.presenter.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreensAdapter (
    fm: Fragment,
    private val bookmark: Fragment,
    private val home: Fragment,
    private val history: Fragment,
    ) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> bookmark
        1 -> home
        2 -> history
        else -> home
    }
}