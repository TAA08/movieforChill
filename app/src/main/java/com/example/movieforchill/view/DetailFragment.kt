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
import com.example.movieforchill.databinding.FragmentDetailBinding
import com.example.movieforchill.viewmodel.ViewModelProviderFactory
import com.example.movieforchill.viewmodel.detail.DetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class DetailFragment : Fragment(), CoroutineScope {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewModel : DetailViewModel
    private val movieId : Int = args.movieId
    private lateinit var prefSettings: SharedPreferences

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        prefSettings = context?.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE) as SharedPreferences
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        getMovieDetails()

    }
    private fun getSessionId() {
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {
        }
    }

    private fun initViewModel(){
        val viewModelProviderFactory = ViewModelProviderFactory(requireActivity())
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[DetailViewModel::class.java]
    }


    private fun getMovieDetails() {
        viewModel.getMovieDetails(movieId)
        viewModel.loadingState.observe(viewLifecycleOwner){
            when(it){
                is DetailViewModel.StateDetail.ShowLoading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is DetailViewModel.StateDetail.HideLoading -> {
                    binding.progressBar.visibility = View.GONE
                }
                is DetailViewModel.StateDetail.Finish -> {
                    viewModel.liveDataDetail.observe(viewLifecycleOwner){
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