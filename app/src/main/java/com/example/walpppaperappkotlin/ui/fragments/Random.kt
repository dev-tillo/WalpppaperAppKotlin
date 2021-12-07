package com.example.walpppaperappkotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.walpppaperappkotlin.MainActivity
import com.example.walpppaperappkotlin.R
import com.example.walpppaperappkotlin.adapters.HomeAdapter
import com.example.walpppaperappkotlin.databinding.FragmentRandomBinding
import com.example.walpppaperappkotlin.models.RandomClass
import com.example.walpppaperappkotlin.models.UnSplashUsers
import com.example.walpppaperappkotlin.retrofit.ApiClient
import com.example.walpppaperappkotlin.ui.fragments.tayyorlar.NetworkHelper
import com.example.walpppaperappkotlin.utils.SplashViewModel
import com.example.walpppaperappkotlin.utils.ViewModelFactory
import com.example.walpppaperappkotlin.utils.kerak.SplashResourceRandom
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Random : Fragment() {

    private val ARG_PARAM1 = "param1"
    private var param1: String? = null

    lateinit var fragment: FragmentRandomBinding
    private lateinit var adapter: HomeAdapter
    private lateinit var splashViewModel: SplashViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragment = FragmentRandomBinding.inflate(inflater, container, false)
        (activity as MainActivity?)?.showBlurView()

        adapter = HomeAdapter(requireContext(), object : HomeAdapter.onItemClicked {
            override fun onCliked(url: RandomClass, position: Int) {
                Toast.makeText(requireContext(),
                    "Hozircha rasmni ko`ra olmaysiz",
                    Toast.LENGTH_SHORT).show()
            }
        })

        fragment.recycler.adapter = adapter

        val networkHelper = NetworkHelper(requireContext())
        splashViewModel = ViewModelProvider(this,
            ViewModelFactory(networkHelper))[SplashViewModel::class.java]
        splashViewModel.fetchSplashRandom().observe(viewLifecycleOwner, {
            when (it) {
                is SplashResourceRandom.Loading -> {
                    fragment.recycler.visibility = View.GONE
                }
                is SplashResourceRandom.Success -> {
                    fragment.recycler.visibility = View.VISIBLE
                    adapter.submitList(it.list)
                }
                is SplashResourceRandom.Error -> {
                    fragment.recycler.visibility = View.GONE
                    val textview = TextView(requireContext())
                    val layoutParam = ConstraintLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParam.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                    layoutParam.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
                    layoutParam.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                    layoutParam.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    textview.text = it.message
                    textview.layoutParams = layoutParam
                }
            }
        })
        return fragment.root
    }
}