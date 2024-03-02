package com.stechoq.quiz3

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stechoq.quiz3.model.PhotoModel
import com.stechoq.quiz3.model.PhotoResponse
import com.stechoq.quiz3.network.PhotoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val data = MutableLiveData<List<PhotoModel>>()
    private val status = MutableLiveData<PhotoApi.ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(PhotoApi.ApiStatus.LOADING)
            try {
                val photos = getUserResponse()
                data.postValue(photos)
                Log.d("MainViewModel", "Success: $photos")
                status.postValue(PhotoApi.ApiStatus.SUCCESS)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(PhotoApi.ApiStatus.FAILED)
            }
        }
    }

    private suspend fun getUserResponse(): List<PhotoModel> {
        return PhotoApi.service.getPhotos()
    }


    fun getData(): LiveData<List<PhotoModel>> = data

    fun getStatus(): LiveData<PhotoApi.ApiStatus> = status
}