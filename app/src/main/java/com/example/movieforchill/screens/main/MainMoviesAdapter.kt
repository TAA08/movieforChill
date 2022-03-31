package com.example.movieforchill.screens.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieforchill.databinding.MoviesItemBinding
import com.squareup.picasso.Picasso


class MainMoviesAdapter(list: List<Result>) :
    RecyclerView.Adapter<MainMoviesAdapter.MainMoviesViewHolder>() {
    class MainMoviesViewHolder(val binding: MoviesItemBinding) : RecyclerView.ViewHolder(binding.root)

    var listOfMovies = list
    private val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    var onMovieClickListener: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMoviesViewHolder {
        return MainMoviesViewHolder(
            MoviesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainMoviesViewHolder, position: Int) {
        with(holder.binding) {
            movieTitle.text = listOfMovies[position].title
            Picasso.get().load(IMAGE_URL+listOfMovies[position].posterPath).into(movieImage)

            root.setOnClickListener {
                onMovieClickListener?.onMovieClick(listOfMovies[position])// передаём все сведения о фильме при клике на постер
                true // нужно для перехода в детали фильма при клике на постер с фильмом
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }

    //создаём интерфейс, который по клику на картинку даёт список результата одного фильма(название. дату релиза, оценки и т.д.)
    interface OnMovieClickListener {
        fun onMovieClick(result: Result)
    }

}


