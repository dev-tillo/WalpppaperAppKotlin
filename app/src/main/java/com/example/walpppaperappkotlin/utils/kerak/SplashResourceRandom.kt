package com.example.walpppaperappkotlin.utils.kerak

import com.example.walpppaperappkotlin.models.RandomClass

sealed class SplashResourceRandom {

    object Loading: SplashResourceRandom()

    class Success(val list: List<RandomClass>): SplashResourceRandom()

    class Error(val message: String): SplashResourceRandom()

}