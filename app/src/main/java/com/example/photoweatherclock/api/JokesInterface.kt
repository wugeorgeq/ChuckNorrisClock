package com.example.photoweatherclock.api

import com.example.photoweatherclock.api.models.ChuckNorrisJoke
import io.reactivex.Single
import retrofit2.http.GET

interface JokesInterface {
    @GET("jokes/random")
    fun getRandomJoke(): Single<ChuckNorrisJoke>
}