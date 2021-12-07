package com.example.walpppaperappkotlin.ui.fragments.tayyorlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.walpppaperappkotlin.MainActivity
import com.example.walpppaperappkotlin.R
import com.example.walpppaperappkotlin.databinding.FragmentAboutBinding

class About : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity?)?.showOnlyBlurView()
    }
}