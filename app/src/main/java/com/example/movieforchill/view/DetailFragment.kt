package com.example.movieforchill.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.movieforchill.R
import com.example.movieforchill.databinding.FragmentDetailBinding
import com.example.movieforchill.viewmodel.ViewModelProviderFactory
import com.example.movieforchill.viewmodel.detail.DetailViewModel
import com.example.movieforchill.viewmodel.main.MovieViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class DetailFragment : Fragment(), CoroutineScope {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private  val viewModel by viewModel<DetailViewModel>()

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
        binding = FragmentDetailBinding.inflate(inflater, container, false)
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
                    binding.ivFavIcon.visibility = View.VISIBLE
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