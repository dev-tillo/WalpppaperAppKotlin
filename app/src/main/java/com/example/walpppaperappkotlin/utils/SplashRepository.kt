package com.example.walpppaperappkotlin.utils

import com.example.walpppaperappkotlin.retrofit.ApiServise
import kotlinx.coroutines.flow.flow

class SplashRepository(private val apiService: ApiServise) {

    fun getSplash(query: String, page: Int) = flow { emit(apiService.getUsers(query, page)) }

    fun getSplashRandom() = flow { emit(apiService.getRandom()) }
}