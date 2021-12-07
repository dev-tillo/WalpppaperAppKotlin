package com.example.walpppaperappkotlin.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.walpppaperappkotlin.R
import com.example.walpppaperappkotlin.adapters.HomePagerAdapter
import com.example.walpppaperappkotlin.databinding.FragmentHomeBinding
import com.example.walpppaperappkotlin.databinding.ItemTabBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.google.gson.reflect.TypeToken

private const val ARG_PARAM1 = "param1"

class HomeFragment : Fragment() {

    private var param1: String? = null

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var list: ArrayList<String>
    private lateinit var adapter: HomePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        loadCategory()

        adapter = HomePagerAdapter(requireParentFragment(), list)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager
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

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
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

        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}