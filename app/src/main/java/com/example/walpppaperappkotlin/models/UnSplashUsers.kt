package com.example.walpppaperappkotlin.models

data class UnSplashUsers(
    val results: List<Result>,
    val total: Int,
    val total_pages: Int
)