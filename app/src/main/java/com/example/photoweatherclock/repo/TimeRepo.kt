package com.example.photoweatherclock.repo

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimeRepo @Inject constructor() {

    /**
     * returns an observable that emits the formatted time, every second
     */
    companion object {
        val dateFormat = SimpleDateFormat("HH:mm:ss")
    }

    fun timeObservable(): Observable<String> {
        return Observable.interval(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .map {
                val currentTime = Calendar.getInstance().time
                dateFormat.format(currentTime)
            }
    }
}