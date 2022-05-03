package com.example.movieforchill.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movieforchill.databinding.FavouriteFragmentBinding
import com.example.movieforchill.model.Result
import com.example.movieforchill.view.adapter.MainMoviesAdapter
import com.example.movieforchill.viewmodel.ViewModelProviderFactory
import com.example.movieforchill.viewmodel.favourite.FavouriteViewModel
import com.example.movieforchill.viewmodel.main.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavouriteFragment : Fragment(), CoroutineScope {

    private lateinit var binding: FavouriteFragmentBinding
    private lateinit var viewModel: FavouriteViewModel

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val adapter = MainMoviesAdapter()


    private lateinit var prefSettings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        prefSettings = context?.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE) as SharedPreferences
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavouriteFragmentBinding.inflate(inflater, container, false)
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
        val viewModelProviderFactory = ViewModelProviderFactory(requireActivity())
        viewModel = ViewModelProvider(
            this, viewModelProviderFactory)[FavouriteViewModel::class.java]

        viewModel.loadingState.observe(viewLifecycleOwner){
            when(it){
                is MovieViewModel.State.ShowLoading -> binding.favprogressBar.visibility = View.VISIBLE
                is MovieViewModel.State.HideLoading -> binding.favprogressBar.visibility = View.GONE
                is MovieViewModel.State.Finish -> viewModel.movies.observe(viewLifecycleOwner){
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
                    val action = FavouriteFragmentDirections.actionFavouritesMovieFragmentToDetailFragment(result.id)
                    findNavController().navigate(action)
                }

            }
        }
    }

    companion object {

        private var sessionId: String = ""
    }



}