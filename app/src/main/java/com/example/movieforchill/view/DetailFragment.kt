package com.example.movieforchill.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.movieforchill.R
import com.example.movieforchill.databinding.DetailFragmentBinding
import com.example.movieforchill.viewmodel.ViewModelProviderFactory
import com.example.movieforchill.viewmodel.detail.DetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class DetailFragment : Fragment(), CoroutineScope {

    private lateinit var binding: DetailFragmentBinding
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewModel: DetailViewModel

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private lateinit var prefSettings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefSettings = context?.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSessionId()
        initViewModel()
        getMovieDetails()

        setOnClickFavourites()
        setLike()

    }

    private fun getSessionId() {
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {
        }
    }

    private fun initViewModel() {
        val viewModelProviderFactory = ViewModelProviderFactory(requireActivity())
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[DetailViewModel::class.java]
    }

    private fun setOnClickFavourites() {

        binding.ivFavIcon.setOnClickListener {
            viewModel.addOrDeleteFavorite(args.movieId, sessionId)
        }
    }

    private fun setLike() {
        viewModel.liveDataDetail.observe(viewLifecycleOwner) {

            if (it.favouriteState) {
                binding.ivFavIcon.setImageResource(R.drawable.ic_baseline_like)
            } else {
                binding.ivFavIcon.setImageResource(R.drawable.ic_baseline_not_like)
            }
        }
    }


    private fun getMovieDetails() {
        viewModel.getMovieDetails(args.movieId, sessionId)
        viewModel.loadingState.observe(viewLifecycleOwner) { it ->
            when (it) {
                is DetailViewModel.StateDetail.ShowLoading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is DetailViewModel.StateDetail.HideLoading -> {
                    binding.progressBar.visibility = View.GONE
                }
                is DetailViewModel.StateDetail.Finish -> {
                    viewModel.liveDataDetail.observe(viewLifecycleOwner) {
                        Picasso.get().load(IMAGE_URL + it.posterPath).into(binding.ivDetailIcon)
                        binding.movieTitle.text = it.title
                        binding.movieOverview.text = it.overview
                        binding.dateRelease.text = it.releaseDate
                    }
                }
            }
        }
    }


    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        private var sessionId: String = ""
    }
}