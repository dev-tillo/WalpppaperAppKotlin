package com.example.walpppaperappkotlin.utils

import com.example.walpppaperappkotlin.models.UnSplashUsers

sealed class SplashResource {

    object Loading : SplashResource()

    class Success(val unsplash: UnSplashUsers) : SplashResource()

    class Error(val message: String) : SplashResource()

}