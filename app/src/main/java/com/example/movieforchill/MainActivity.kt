package com.example.movieforchill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieforchill.data.retrofit.api.RetrofitInstance
import com.example.movieforchill.databinding.ActivityMainBinding
import com.example.movieforchill.screens.main.MainFragment
import com.example.movieforchill.screens.main.MainMoviesAdapter
import com.example.movieforchill.screens.main.MoviesModel
import com.google.android.material.navigation.NavigationBarView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = findNavController(R.id.nav_host_fragment)
        initBottomNav()
        initOnDestinationChangedListener()
    }

    private fun initOnDestinationChangedListener() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.firstFragment,
                R.id.secondFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.blankFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }

    private fun initBottomNav() {
        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.bottomNavigation.setupWithNavController(navController)
    }




}