package com.example.movieforchill.screens.favourite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieforchill.R
import com.example.movieforchill.databinding.FavouriteFragmentBinding

class FavouriteFragment : Fragment() {

    private lateinit var binding: FavouriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavouriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

}