package com.example.movieforchill.model.di

import android.content.Context
import android.content.SharedPreferences
import com.example.movieforchill.model.retrofit.api.MoviesApiService
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import com.example.movieforchill.model.room.dao.MovieDao
import com.example.movieforchill.model.room.repository.LoginRepository
import com.example.movieforchill.model.room.repository.MovieDatabase
import com.example.movieforchill.model.room.repository.MovieRepository
import com.example.movieforchill.viewmodel.MainViewModel
import com.example.movieforchill.viewmodel.detail.DetailViewModel
import com.example.movieforchill.viewmodel.favourite.FavouriteViewModel
import com.example.movieforchill.viewmodel.login.LoginViewModel
import com.example.movieforchill.viewmodel.main.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val networkModule = module {
    single { getMovieApi() }
}

val daoModule = module {
    single { getMovieDao(context = get()) }
    single { getSharedPreferences(context = get()) }
}

val repositoryModule = module {
    single { MovieRepository(application = get(), api = get(), db = get()) }
    single { LoginRepository(application = get(), workWithApi = get(), prefSettings = get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(repository = get()) }
    viewModel { DetailViewModel(repository = get()) }
    viewModel { FavouriteViewModel(repository = get()) }
    viewModel { LoginViewModel(application = get(), repository = get()) }
    viewModel { MainViewModel(repository = get()) }

}

val appModule = networkModule + daoModule + repositoryModule + viewModelModule

private fun getMovieApi(): MoviesApiService = RetrofitInstance.getPostApi()
private fun getMovieDao(context: Context): MovieDao = MovieDatabase.getDatabase(context).movieDao()
private fun getSharedPreferences(context: Context) : SharedPreferences{
 return context.getSharedPreferences(
     "Settings",
     Context.MODE_PRIVATE
 ) as SharedPreferences
}