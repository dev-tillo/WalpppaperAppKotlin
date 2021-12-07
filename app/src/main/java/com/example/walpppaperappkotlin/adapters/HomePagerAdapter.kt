package com.example.walpppaperappkotlin.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.walpppaperappkotlin.ui.home.BlankFragment

class HomePagerAdapter(fm: Fragment, var list: List<String>) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return BlankFragment.newInstance(list[position])
    }
}