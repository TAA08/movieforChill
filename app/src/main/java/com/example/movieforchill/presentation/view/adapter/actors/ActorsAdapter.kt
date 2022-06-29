package com.example.movieforchill.presentation.view.adapter.actors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.movieforchill.databinding.ActorsItemBinding
import com.example.movieforchill.domain.models.actors.Cast
import com.squareup.picasso.Picasso

class ActorsAdapter :  ListAdapter<Cast, ActorsViewHolder>(ActorDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        return  ActorsViewHolder(
            ActorsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        val actor = getItem(position)
        with(holder.binding){
            actorName.text = actor.name
            Picasso.get().load(IMAGE_URL + actor.profilePath).into(actorImage)
            character.text = actor.character
        }
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}