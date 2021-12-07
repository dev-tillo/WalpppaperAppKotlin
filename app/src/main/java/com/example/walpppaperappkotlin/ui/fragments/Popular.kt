package com.example.walpppaperappkotlin.ui.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.walpppaperappkotlin.MainActivity
import com.example.walpppaperappkotlin.R
import com.example.walpppaperappkotlin.adapters.HomePagerAdapter
import com.example.walpppaperappkotlin.databinding.FragmentPopularBinding
import com.example.walpppaperappkotlin.databinding.ItemTabBinding
import com.example.walpppaperappkotlin.models.Result
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.google.gson.reflect.TypeToken

class Popular : Fragment() {

    lateinit var fragment: FragmentPopularBinding
    private lateinit var list: ArrayList<String>
    private lateinit var adapter: HomePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragment = FragmentPopularBinding.inflate(inflater, container, false)
        (activity as MainActivity?)?.showBlurView()

        loadCategory()

        adapter = HomePagerAdapter(requireParentFragment(), list)
        fragment.viewpager.adapter = adapter

        TabLayoutMediator(fragment.tabLayout, fragment.viewpager
        ) { tab, position ->
            val itemTabBinding: ItemTabBinding = ItemTabBinding.inflate(layoutInflater)
            tab.customView = itemTabBinding.root
            itemTabBinding.text.text = list[position]
            if (position == 0) {
                itemTabBinding.circle.visibility = View.VISIBLE
                itemTabBinding.text.setTextColor(Color.WHITE)
            } else {
                itemTabBinding.circle.visibility = View.INVISIBLE
                itemTabBinding.text.setTextColor(Color.parseColor("#808a93"))
            }
        }.attach()

        fragment.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val itemTabBinding: ItemTabBinding = ItemTabBinding.bind(tab.customView!!)
                itemTabBinding.circle.visibility = View.VISIBLE
                itemTabBinding.text.setTextColor(Color.WHITE)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val itemTabBinding: ItemTabBinding = ItemTabBinding.bind(tab.customView!!)
                itemTabBinding.circle.visibility = View.INVISIBLE
                itemTabBinding.text.setTextColor(Color.parseColor("#808a93"))
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return fragment.root
    }

    private fun loadCategory() {
        list = ArrayList()

        list.add("ALL")
        list.add("NEW")
        list.add("CAR")
        list.add("DESIGN")
        list.add("WOOD")
        list.add("SMILE")
        list.add("AIRPLAN")
        list.add("HOME")
        list.add("FOOD")
        list.add("PHONE")
        list.add("TECHNOLOGY")
        list.add("LAPTOP")
        list.add("MOBILE")
        list.add("EPOXY")
        list.add("TEACHING")
        list.add("EATING")
        list.add("CLASSROM")
        list.add("FRUITS")
    }
}