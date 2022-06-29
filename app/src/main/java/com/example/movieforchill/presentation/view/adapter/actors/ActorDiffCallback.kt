package com.example.movieforchill.presentation.view.adapter.actors

import androidx.recyclerview.widget.DiffUtil
import com.example.movieforchill.domain.models.actors.Cast

object ActorDiffCallback : DiffUtil.ItemCallback<Cast>() {
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return  oldItem == newItem
    }
}