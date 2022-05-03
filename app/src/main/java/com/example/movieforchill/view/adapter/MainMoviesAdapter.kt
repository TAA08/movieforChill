package com.example.movieforchill.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.movieforchill.databinding.MoviesItemBinding
import com.example.movieforchill.model.Result
import com.squareup.picasso.Picasso


class MainMoviesAdapter: ListAdapter<Result, MainMoviesViewHolder>(MovieDiffCallback) {


    private val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    var onMovieClickListener: OnMovieClickListener? = null
    var onReachEndListener: OnReachEndListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMoviesViewHolder {
        return MainMoviesViewHolder(
            MoviesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainMoviesViewHolder, position: Int) {
        val movie = getItem(position)
        if (position >= (itemCount - 3) && onReachEndListener != null) {
            onReachEndListener?.onReachEnd()
        }
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

    interface OnReachEndListener {
        fun onReachEnd()
    }

}

