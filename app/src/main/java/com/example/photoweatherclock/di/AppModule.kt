package com.example.photoweatherclock.di

import android.content.Context
import com.example.photoweatherclock.api.JokesInterface
import com.example.photoweatherclock.api.PhotosInterface
import com.example.photoweatherclock.repo.JokesRepo
import com.example.photoweatherclock.repo.JokesRepoImpl
import com.example.photoweatherclock.repo.PhotosRepo
import com.example.photoweatherclock.repo.PhotosRepoImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
class AppModule(private val applicationContext: Context) {
    @Provides
    fun provideApplicationContext(): Context = applicationContext

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun providePhotoRetrofitClient(client: OkHttpClient): PhotosInterface {
        val builder = Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        return builder.client(client).build().create(PhotosInterface::class.java)
    }

    @Provides
    fun provideJokesRetrofitClient(client: OkHttpClient): JokesInterface {
        val builder = Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        return builder.client(client).build().create(JokesInterface::class.java)
    }

    @Provides
    fun providePhotosRepo(impl: PhotosRepoImpl): PhotosRepo = impl

    @Provides
    fun provideJokesRepo(impl: JokesRepoImpl): JokesRepo = impl

}
