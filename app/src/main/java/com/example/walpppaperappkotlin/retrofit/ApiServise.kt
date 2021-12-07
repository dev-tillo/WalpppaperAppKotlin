package com.example.walpppaperappkotlin.retrofit

import com.example.walpppaperappkotlin.models.RandomClass
import com.example.walpppaperappkotlin.models.UnSplashUsers
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServise {

    @GET("search/photos/?client_id=1pmL0rF2x7Bu5Jguvjhb0XsZk9nCWCS45pGogYggT4o&per_page=20&")
    suspend fun getUsers(
        @Query("query") query: String, @Query("page") page: Int,
    ): Response<UnSplashUsers>

    @GET("photos/random/?client_id=1pmL0rF2x7Bu5Jguvjhb0XsZk9nCWCS45pGogYggT4o&count=20")
    suspend fun getRandom(): Response<List<RandomClass>>

}