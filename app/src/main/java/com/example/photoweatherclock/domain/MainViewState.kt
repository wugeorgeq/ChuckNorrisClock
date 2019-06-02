package com.example.photoweatherclock.domain

data class MainViewState(
    val photoUrl: String,
    val joke: String,
    val time: String
)

sealed class PartialViewState {
    data class PhotoUrl(val photoUrl: String) : PartialViewState()
    data class Joke(val joke: String) : PartialViewState()
    data class Time(val time: String) : PartialViewState()
}