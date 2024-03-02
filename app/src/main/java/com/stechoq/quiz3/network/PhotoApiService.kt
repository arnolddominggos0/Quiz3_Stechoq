package com.stechoq.quiz3.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.stechoq.quiz3.model.PhotoModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface PhotoApiService {
    @GET("photos")
    suspend fun getPhotos(): List<PhotoModel>
}

object PhotoApi {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val service: PhotoApiService by lazy {
        retrofit.create(PhotoApiService::class.java)
    }

    enum class ApiStatus { LOADING, SUCCESS, FAILED }
}
