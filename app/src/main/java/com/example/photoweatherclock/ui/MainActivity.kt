package com.example.photoweatherclock.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.photoweatherclock.App
import com.example.photoweatherclock.R
import com.example.photoweatherclock.di.AppComponent
import com.example.photoweatherclock.domain.MainViewModel
import com.example.photoweatherclock.domain.MainViewState
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var component: AppComponent

    private val disposables = CompositeDisposable()

    private val crossFadeFactory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component = (application as App).appComponent

        val provider = ViewModelProviders.of(this, MainViewModel.Factory(component))
        viewModel = provider[MainViewModel::class.java]

        subscribe()
    }

    private fun subscribe() {
        disposables.add(viewModel.viewState()
            .subscribe(
                {
                    render(it)
                },
                {
                    Log.e("MainActivity", it.message)
                }
            )

        )
    }

    private fun render(viewState: MainViewState) {
        textView.text = viewState.time
        jokeTextView.text = viewState.joke
        Glide.with(this)
            .load(viewState.photoUrl)
            .transition(withCrossFade(crossFadeFactory))
            .placeholder(imageView.drawable)
            .into(imageView)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

}
