package com.example.movieforchill.presentation.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.movieforchill.domain.models.movie.Result


object MovieDiffCallback : DiffUtil.ItemCallback<Result>() {

    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}