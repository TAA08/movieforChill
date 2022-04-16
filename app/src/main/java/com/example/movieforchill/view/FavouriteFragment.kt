package com.example.movieforchill.view

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.movieforchill.R
import com.example.movieforchill.databinding.FavouriteFragmentBinding
import com.example.movieforchill.model.Result
import com.example.movieforchill.model.Session
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import com.example.movieforchill.view.adapter.favourite_adapter.FavouriteAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavouriteFragment : Fragment(), CoroutineScope {

    private lateinit var binding: FavouriteFragmentBinding

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val adapter = FavouriteAdapter()


    private lateinit var prefSettings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        prefSettings = context?.getSharedPreferences(LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE) as SharedPreferences
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavouriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        downloadData()
//        onMovieClickListener()
//        onBackPressed()
//
//        data.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//            binding.rvFavmovies.adapter = adapter
//        }
//    }

//    private fun downloadData() {
//
//        binding.favprogressBar.visibility = View.VISIBLE
//        val sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
//        if (sessionId.isNotEmpty()) {
//            launch {
//
//                data.value = RetrofitInstance.getPostApi().getFavorites(
//                    session_id = sessionId,
//                    page = PAGE
//                ).results
//                binding.favprogressBar.visibility = View.GONE
//            }
//        }
//    }

}