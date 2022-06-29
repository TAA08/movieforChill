package com.example.movieforchill.presentation.favourite_movie

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movieforchill.databinding.FragmentFavouriteBinding
import com.example.movieforchill.domain.models.movie.Result
import com.example.movieforchill.presentation.view.adapter.movie.MainMoviesAdapter
import com.example.movieforchill.presentation.movie.MovieViewModel
import com.example.movieforchill.presentation.login.LoginFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class FavouriteFragment : Fragment(), CoroutineScope {

    private lateinit var binding: FragmentFavouriteBinding
    private val viewModel by viewModel<FavouriteViewModel>()

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val adapter = MainMoviesAdapter()


    private lateinit var prefSettings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        prefSettings = context?.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSessionId()
        initAndObserveViewModel()
        viewModel.getPosts(sessionId)
        getClick()
    }

    private fun getSessionId() {
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {
        }
    }

    private fun initAndObserveViewModel() {

        viewModel.loadingState.observe(viewLifecycleOwner) { it ->
            when (it) {
                is MovieViewModel.State.ShowLoading -> binding.favprogressBar.visibility =
                    View.VISIBLE
                is MovieViewModel.State.HideLoading -> binding.favprogressBar.visibility = View.GONE
                is MovieViewModel.State.Finish -> viewModel.movies.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    binding.rvFavmovies.adapter = adapter
                }
            }
        }

    }

    private fun getClick() {
        launch {
            binding.rvFavmovies.adapter = adapter
            adapter.onMovieClickListener = object : MainMoviesAdapter.OnMovieClickListener {
                override fun onMovieClick(result: Result) {
                    val action =
                        FavouriteFragmentDirections.actionFavouritesMovieFragmentToDetailFragment(
                            result.id
                        )
                    findNavController().navigate(action)
                }

            }
        }
    }

    companion object {

        private var sessionId: String = ""
    }


}