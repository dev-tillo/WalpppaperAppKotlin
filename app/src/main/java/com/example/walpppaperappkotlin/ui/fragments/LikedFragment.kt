package com.example.walpppaperappkotlin.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.walpppaperappkotlin.MainActivity
import com.example.walpppaperappkotlin.R
import com.example.walpppaperappkotlin.adapters.EntityAdapter
import com.example.walpppaperappkotlin.adapters.HomeAdapter
import com.example.walpppaperappkotlin.databace.DataHelper
import com.example.walpppaperappkotlin.databace.Entity
import com.example.walpppaperappkotlin.databinding.FragmentLikedBinding
import com.example.walpppaperappkotlin.models.Result
import com.example.walpppaperappkotlin.models.UnSplashUsers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LikedFragment : Fragment() {

    lateinit var fragment: FragmentLikedBinding
    private lateinit var entityAdapter: EntityAdapter
    private lateinit var dataHelper: DataHelper

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragment = FragmentLikedBinding.inflate(inflater, container, false)
        (activity as MainActivity?)?.showBlurView()
        dataHelper = DataHelper.getInstance(requireContext())

        entityAdapter = EntityAdapter(requireContext(), object : EntityAdapter.onItemClicked {
            override fun onCliked(url: Entity) {
                var bundle = Bundle()
                bundle.putSerializable("image", url)
                bundle.putInt("key1", 2)
                findNavController().navigate(R.id.imageFragment, bundle)
            }
        })
        fragment.recycler.adapter = entityAdapter

        dataHelper.pdpDao()
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                entityAdapter.submitList(it)
            }

        return fragment.root
    }
}