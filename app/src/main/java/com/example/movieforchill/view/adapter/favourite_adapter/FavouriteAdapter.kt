package com.example.movieforchill.view.adapter.favourite_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.movieforchill.databinding.MoviesItemBinding
import com.example.movieforchill.model.Result
import com.squareup.picasso.Picasso

class FavouriteAdapter : ListAdapter<Result, FavouritesMoviesViewHolder>(FavouritesMovieDiffCallback) {


    private val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    var onMovieClickListener: OnMovieClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesMoviesViewHolder {
        return FavouritesMoviesViewHolder(
            MoviesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FavouritesMoviesViewHolder, position: Int) {
        val movie = getItem(position)

        with(holder.binding) {
            movieTitle.text = movie.title
            Picasso.get().load(IMAGE_URL+movie.posterPath).into(movieImage)

            root.setOnClickListener {
                onMovieClickListener?.onMovieClick(movie)// передаём все сведения о фильме при клике на постер
                true // нужно для перехода в детали фильма при клике на постер с фильмом
            }
        }
    }

    //создаём интерфейс, который по клику на картинку даёт список результата одного фильма(название. дату релиза, оценки и т.д.)
    interface OnMovieClickListener {
        fun onMovieClick(result: Result)
    }

}