package com.example.movieforchill.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movieforchill.R
import com.example.movieforchill.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = findNavController(R.id.nav_host_fragment)
        bottomBarVisibility()
        initBottomNav()
    }

    private fun bottomBarVisibility() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.filmsFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.favouritesMovieFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.detailFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.loginFragment -> {
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