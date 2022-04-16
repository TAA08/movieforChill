package com.example.movieforchill.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import com.example.movieforchill.databinding.MainFragmentBinding
import com.example.movieforchill.model.Result
import com.example.movieforchill.view.adapter.main_adapter.MainMoviesAdapter
import com.example.movieforchill.viewmodel.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainFragment : Fragment(), CoroutineScope {


    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = MainMoviesAdapter()

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private var oldList = mutableListOf<Result>()
    private var newList = mutableListOf<Result>()
    private var movies: MutableLiveData<List<Result>> = MutableLiveData()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // в этом методе прописывается логика
        super.onViewCreated(view, savedInstanceState)
        downloadData()
        onScrollListener()
        getClick()
        //setAdapter()
        movies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }


        binding.rvMovies.adapter = adapter

    }

    private fun getClick() {
        launch {
            binding.rvMovies.adapter = adapter
            adapter.onMovieClickListener = object : MainMoviesAdapter.OnMovieClickListener {
                override fun onMovieClick(result: Result) {
                    val action = MainFragmentDirections.actionFirstFragmentToDetailFragment(result)
                    findNavController().navigate(action)
                }

            }
        }
    }

    private fun downloadData() {

        binding.progressBar.visibility = View.VISIBLE
        launch {
            val result = RetrofitInstance.getPostApi().getMoviesList(page = PAGE).results

            newList.clear()
            for (movie in result) {
                newList.add(movie)
            }

            oldList += newList

            movies.postValue(oldList.toList())

            binding.progressBar.visibility = View.GONE
        }
    }

    /*private fun initAndObserveViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.liveData.observe(
            this,
            {

            }
        )
    }*/

    private fun onScrollListener() {

        adapter.onReachEndListener = object : MainMoviesAdapter.OnReachEndListener {
            override fun onReachEnd() {
                PAGE++
                downloadData()
            }
        }
    }

    companion object {

        var PAGE = 1
    }
}