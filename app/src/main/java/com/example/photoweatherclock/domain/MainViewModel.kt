package com.example.photoweatherclock.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photoweatherclock.di.AppComponent
import com.example.photoweatherclock.repo.JokesRepo
import com.example.photoweatherclock.repo.PhotosRepo
import com.example.photoweatherclock.repo.TimeRepo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import java.util.concurrent.Callable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val photosRepo: PhotosRepo,
    private val timeRepo: TimeRepo,
    private val jokesRepo: JokesRepo
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory(private val component: AppComponent) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return component.mainViewModel() as T
        }
    }

    /**
     * This observable emits new data based on the underlying repositories
     *
     * Return type is one data class that holds all the data the ui needs
     */
    fun viewState(): Observable<MainViewState> {
        val mergedObservables = Observable.merge(photosObservable(), timeObservable(), jokesObservable())
        return mergedObservables.scanWith(Callable {
            MainViewState("", "", "")
        }, BiFunction(partialStateReducer))
            .observeOn(AndroidSchedulers.mainThread())
    }

    private val partialStateReducer = { viewState: MainViewState, partial: PartialViewState ->
        when (partial) {
            is PartialViewState.PhotoUrl -> viewState.copy(photoUrl = partial.photoUrl)
            is PartialViewState.Time -> viewState.copy(time = partial.time)
            is PartialViewState.Joke -> viewState.copy(joke = partial.joke)
        }
    }

    private fun photosObservable(): Observable<PartialViewState> {
        return photosRepo.newPhotoByInterval().map { PartialViewState.PhotoUrl(it) }
    }

    private fun timeObservable(): Observable<PartialViewState> {
        return timeRepo.timeObservable().map { PartialViewState.Time(it) }
    }

    private fun jokesObservable(): Observable<PartialViewState> {
        return jokesRepo.newJokeByInterval().map { PartialViewState.Joke(it) }
    }

}
