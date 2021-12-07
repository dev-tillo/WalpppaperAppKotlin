package com.example.walpppaperappkotlin.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walpppaperappkotlin.ui.fragments.tayyorlar.NetworkHelper

class ViewModelFactory(private val networkHelper: NetworkHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(networkHelper) as T
        }
        throw Exception("Error")
    }
}