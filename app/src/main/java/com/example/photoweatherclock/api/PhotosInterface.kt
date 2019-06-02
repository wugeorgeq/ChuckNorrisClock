package com.example.photoweatherclock.api

import com.example.photoweatherclock.api.models.Photo
import io.reactivex.Single
import retrofit2.http.GET

interface PhotosInterface {
    @GET("v2/list")
    fun getPhotosList(): Single<List<Photo>>
}