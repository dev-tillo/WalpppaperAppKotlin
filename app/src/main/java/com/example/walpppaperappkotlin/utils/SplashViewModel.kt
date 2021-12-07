package com.example.walpppaperappkotlin.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walpppaperappkotlin.retrofit.ApiClient
import com.example.walpppaperappkotlin.ui.fragments.tayyorlar.NetworkHelper
import com.example.walpppaperappkotlin.utils.kerak.SplashResourceRandom
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashViewModel(private val networkHelper: NetworkHelper) : ViewModel() {

    private val repository = SplashRepository(ApiClient.apiService)

    fun fetchSplash(query: String, page: Int): LiveData<SplashResource> {
        val liveData = MutableLiveData<SplashResource>()
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                liveData.postValue(SplashResource.Loading)
                repository.getSplash(query, page)
                    .catch {
                        liveData.postValue(SplashResource.Error("Error"))
                    }
                    .collect {
                        if (it.isSuccessful) {
                            liveData.postValue(SplashResource.Success(it.body()!!))
                        }
                    }
            }
        } else {
            liveData.postValue(SplashResource.Error("Problem with internet"))
        }

        return liveData
    }

    fun fetchSplashRandom(): LiveData<SplashResourceRandom> {
        val liveData = MutableLiveData<SplashResourceRandom>()
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                liveData.postValue(SplashResourceRandom.Loading)
                repository.getSplashRandom()
                    .catch {
                        liveData.postValue(SplashResourceRandom.Error("Error"))
                    }
                    .collect {
                        if (it.isSuccessful) {
                            liveData.postValue(SplashResourceRandom.Success(it.body()!!))
                        }
                    }
            }
        } else {
            liveData.postValue(SplashResourceRandom.Error("Problem with internet"))
        }

        return liveData
    }
}