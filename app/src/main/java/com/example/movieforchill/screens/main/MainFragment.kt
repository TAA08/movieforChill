package com.example.movieforchill.screens.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieforchill.R
import com.example.movieforchill.data.retrofit.api.RetrofitInstance
import com.example.movieforchill.databinding.ActivityMainBinding
import com.example.movieforchill.databinding.MainFragmentBinding
import com.example.movieforchill.screens.detail.DetailFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Result
import kotlin.coroutines.CoroutineContext

class MainFragment : Fragment(), CoroutineScope {


    private lateinit var binding: MainFragmentBinding

    override val coroutineContext: CoroutineContext = Dispatchers.Main


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // в этом методе прописывается логика
        super.onViewCreated(view, savedInstanceState)
        getPosts()
        //setAdapter()

    }

    private fun getPosts() {
        launch {
            var responce = RetrofitInstance.getPostApi().getMoviesList()
            val adapter = MainMoviesAdapter(list = responce.results)
            binding.rvMovies.adapter = adapter
            adapter.onMovieClickListener = object : MainMoviesAdapter.OnMovieClickListener {
                override fun onMovieClick(result: com.example.movieforchill.screens.main.Result) {
                    val action = MainFragmentDirections.actionFirstFragmentToDetailFragment(result)
                    findNavController().navigate(action)
                }

            }
        }
    }
}