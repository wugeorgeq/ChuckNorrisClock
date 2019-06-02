package com.example.photoweatherclock.repo

import com.example.photoweatherclock.api.JokesInterface
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface JokesRepo {
    fun newJokeByInterval(): Observable<String>
}

class JokesRepoImpl @Inject constructor(
    private val jokesInterface: JokesInterface
) : JokesRepo {

    /**
     * Fetch a new joke every 10 seconds
     */
    override fun newJokeByInterval(): Observable<String> {
        return Observable.interval(0, 10, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .flatMap {
                jokesInterface.getRandomJoke()
                    .toObservable()
                    .map { it.value }
            }
    }
}