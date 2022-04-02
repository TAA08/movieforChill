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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Result

class MainFragment : Fragment() {


    private lateinit var binding: MainFragmentBinding

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
        RetrofitInstance.getPostApi().getMoviesList().enqueue(object : Callback<MoviesModel> {
            override fun onFailure(call: Call<MoviesModel>, t: Throwable) {
                println("ERROR")
            }

            override fun onResponse(call: Call<MoviesModel>, response: Response<MoviesModel>) {
                Log.d("My_post_list", response.body().toString())
                if (response.isSuccessful) {
                    val list = response.body()
                    list?.results?.let {
                        val adapter = MainMoviesAdapter(list = it)
                        binding.rvMovies.adapter = adapter
                        adapter.onMovieClickListener = object : MainMoviesAdapter.OnMovieClickListener {
                            override fun onMovieClick(result: com.example.movieforchill.screens.main.Result) {
                                //detailFragment(result)
                                val action = MainFragmentDirections.actionFirstFragmentToDetailFragment(result)
                                findNavController().navigate(action)

                            }
                        }
                    }
                }
            }
        })
    }


 /*   private fun detailFragment(result: com.example.movieforchill.screens.main.Result) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, DetailFragment.newInstance(result))// передаём во фрагмент деталей детали одного фильма
            .addToBackStack(null)
            .commit()
    } */

}

    /*private fun setAdapter() {
        binding.rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
    }
    создавать Грид можно так а можно прописать в xml как сделано сейчас
    */




