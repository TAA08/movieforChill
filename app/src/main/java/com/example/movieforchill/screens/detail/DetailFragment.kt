package com.example.movieforchill.screens.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.movieforchill.R
import com.example.movieforchill.data.retrofit.api.RetrofitInstance
import com.example.movieforchill.databinding.DetailFragmentBinding
import com.example.movieforchill.screens.main.Result
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailFragment : Fragment(), CoroutineScope {

    private lateinit var binding: DetailFragmentBinding
    private val args: DetailFragmentArgs by navArgs()

    private lateinit var result: Result
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parceMovieDetail()
    } Так нельзя делать */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            result = args.result
            var response = RetrofitInstance.getMovieDetail().getMovieDetail(id = result.id)
            Picasso.get().load(IMAGE_URL + response.posterPath).into(binding.ivDetailIcon)
            binding.movieTitle.text = response.title
            binding.movieOverview.text = response.overview
            binding.dateRelease.text = response.releaseDate
        }

    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}