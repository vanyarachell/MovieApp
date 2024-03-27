package com.vanya.movieapp.di

import android.content.Context
import android.net.ConnectivityManager
import com.vanya.movieapp.MovieApplication
import com.vanya.movieapp.api.ApiService
import com.vanya.movieapp.db.MovieDatabase
import com.vanya.movieapp.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ) = MovieDatabase.invoke(context)

    @Singleton
    @Provides
    fun provideRepository(db: MovieDatabase): MovieRepository {
        return MovieRepository(db)
    }

    @Singleton
    @Provides
    fun provideMovieApplication(): MovieApplication {
        return MovieApplication()
    }

    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}