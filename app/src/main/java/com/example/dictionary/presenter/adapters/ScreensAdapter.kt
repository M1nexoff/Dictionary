package com.example.dictionary.presenter.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreensAdapter (
    fm: Fragment,
    private val translate: Fragment,
    private val speak: Fragment,
    private val favourite: Fragment,
    ) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> translate
        1 -> speak
        2 -> favourite
        else -> translate
    }
}