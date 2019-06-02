package com.example.photoweatherclock.repo

import com.example.photoweatherclock.api.PhotosInterface
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface PhotosRepo {
    fun newPhotoByInterval(): Observable<String>
}

class PhotosRepoImpl @Inject constructor(
    private val photosInterface: PhotosInterface
) : PhotosRepo {

    private var counter = 0

    /**
     * This function fetches a photos list then emits a new photo url every 5 seconds
     */
    override fun newPhotoByInterval(): Observable<String> {
        return photosInterface.getPhotosList()
            .subscribeOn(Schedulers.io())
            .flatMapObservable { photos ->
                Observable.interval(0, 5, TimeUnit.SECONDS)
                    .map {
                        counter++
                        if (counter == photos.size) {
                            counter = 0
                        }
                        photos[counter].download_url
                    }
            }
    }
}
