package com.example.movieforchill.screens.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieforchill.R
import com.example.movieforchill.databinding.DetailFragmentBinding
import com.example.movieforchill.screens.main.Result
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private lateinit var binding: DetailFragmentBinding

    private lateinit var result: Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parceMovieDetail()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(IMAGE_URL + result.posterPath).into(binding.ivDetailIcon)
        binding.movieTitle.text = result.title
        binding.movieOverview.text = result.overview
        binding.dateRelease.text = result.releaseDate
    }

    private fun parceMovieDetail() {
        requireArguments().getParcelable<Result>("result")?.let {
            result = it
        }
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"

        fun newInstance(result: Result):DetailFragment{
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("result", result)
                }
            }
        }
    }
}