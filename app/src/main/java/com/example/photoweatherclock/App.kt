package com.example.photoweatherclock

import android.app.Application
import com.example.photoweatherclock.di.AppModule
import com.example.photoweatherclock.di.DaggerAppComponent

class App : Application() {
    val appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
}
