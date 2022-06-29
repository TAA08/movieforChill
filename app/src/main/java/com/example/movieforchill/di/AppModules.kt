package com.example.movieforchill.di

import android.content.Context
import android.content.SharedPreferences
import com.example.movieforchill.data.net.MoviesApiService
import com.example.movieforchill.data.net.RetrofitInstance
import com.example.movieforchill.data.room.MovieDao
import com.example.movieforchill.data.repository.LoginRepositoryImpl
import com.example.movieforchill.data.room.MovieDatabase
import com.example.movieforchill.data.repository.MovieRepositoryImpl
import com.example.movieforchill.domain.repository.LoginRepository
import com.example.movieforchill.domain.repository.MovieRepository
import com.example.movieforchill.domain.usecases.*
import com.example.movieforchill.presentation.main.MainViewModel
import com.example.movieforchill.presentation.movie_detail.DetailViewModel
import com.example.movieforchill.presentation.favourite_movie.FavouriteViewModel
import com.example.movieforchill.presentation.login.LoginViewModel
import com.example.movieforchill.presentation.movie.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { getMovieApi() }
}

val daoModule = module {
    single { getMovieDao(context = get()) }

}

val sharedPreferences = module {
    single { getSharedPreferences(context = get()) }
    single { getSharedPreferences(context = get()).edit() }
}

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(application = get(), api = get(), db = get()) }
    single<LoginRepository> {
        LoginRepositoryImpl(
            application = get(),
            workWithApi = get(),
            prefSettings = get(),
            editor = get()
        )
    }
}

val useCaseModule = module {
    single { GetMoviesListUseCase(repository = get()) }
    single { GetMovieDetailUseCase(repository = get()) }
    single { ChangeMovieStateUseCase(repository = get()) }
    single { PostMovieStateUseCase(repository = get()) }
    single { GetFavouriteMovieUseCase(repository = get()) }
    single { DeleteSessionUseCase(repository = get()) }
    single { UserLoginUseCase(repository = get()) }
    single { GetCreditsUseCase(repository = get()) }

}

val viewModelModule = module {
    viewModel { MovieViewModel(useCase = get()) }
    viewModel {
        DetailViewModel(
            detailUseCase = get(),
            changeMovieStateUseCase = get(),
            postMovieStateUseCase = get(),
            application = get(),
            getCreditsUseCase = get()
        )
    }
    viewModel { FavouriteViewModel(favouriteMovieUseCase = get()) }
    viewModel { LoginViewModel(application = get(), loginUseCase = get()) }
    viewModel { MainViewModel(deleteSessionUseCase = get()) }

}

val appModule =
    networkModule + daoModule + repositoryModule + viewModelModule + useCaseModule + sharedPreferences

private fun getMovieApi(): MoviesApiService = RetrofitInstance.getPostApi()
private fun getMovieDao(context: Context): MovieDao = MovieDatabase.getDatabase(context).movieDao()
private fun getSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(
        "Settings",
        Context.MODE_PRIVATE
    ) as SharedPreferences
}