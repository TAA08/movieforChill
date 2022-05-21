package com.example.movieforchill.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movieforchill.databinding.FragmentMovieBinding
import com.example.movieforchill.model.movie.Result
import com.example.movieforchill.view.adapter.MainMoviesAdapter
import com.example.movieforchill.viewmodel.main.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class MovieFragment : Fragment(), CoroutineScope {


    private lateinit var binding: FragmentMovieBinding
    private  val viewModel by viewModel<MovieViewModel>()
    private val adapter = MainMoviesAdapter()

    override val coroutineContext: CoroutineContext = Dispatchers.Main


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) { // в этом методе прописывается логика
        super.onViewCreated(view, savedInstanceState)
        initAndObserveViewModel()
        getClick()
        onBackPressed()
    }

    private fun getClick() {
        launch {
            binding.rvMovies.adapter = adapter
            adapter.onMovieClickListener = object : MainMoviesAdapter.OnMovieClickListener {
                override fun onMovieClick(result: Result) {
                    val action =
                        MovieFragmentDirections.actionFirstFragmentToDetailFragment(result.id)
                    findNavController().navigate(action)
                }

            }
        }
    }


    private fun initAndObserveViewModel() {

        viewModel.getPosts(PAGE)


        viewModel.loadingState.observe(viewLifecycleOwner) {
            when (it) {
                is MovieViewModel.State.ShowLoading -> binding.progressBar.visibility = View.VISIBLE
                is MovieViewModel.State.HideLoading -> binding.progressBar.visibility = View.GONE
                is MovieViewModel.State.Finish -> viewModel.movies.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    binding.rvMovies.adapter = adapter
                }
            }
        }

    }

    private fun onBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                requireContext().let {
                    AlertDialog
                        .Builder(it)
                        .setMessage("Выйти?")
                        .setPositiveButton("Да") { dialogInterface, i ->
                            requireActivity().finish()
                        }
                        .setNegativeButton("Нет") { dialogInterface, i -> }
                        .create()
                        .show()
                }

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    companion object{
        private var PAGE = 1
    }
}