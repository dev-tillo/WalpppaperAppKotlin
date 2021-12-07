package com.example.walpppaperappkotlin.utils

import com.example.walpppaperappkotlin.models.UnSplashUsers
import retrofit2.Response

sealed class UserResource {

    object Loading : UserResource()

    class Success(val userWithPost: Response<List<UnSplashUsers>>) : UserResource()

    class Error(val message: String) : UserResource()

}