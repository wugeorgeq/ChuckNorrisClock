package com.example.photoweatherclock.di

import android.content.Context
import com.example.photoweatherclock.api.PhotosInterface
import com.example.photoweatherclock.domain.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun applicationContext(): Context
    fun photoInterface(): PhotosInterface
    fun mainViewModel(): MainViewModel
}
