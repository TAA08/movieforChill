package com.example.movieforchill.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.movieforchill.R
import com.example.movieforchill.databinding.ActivityMainBinding
import com.example.movieforchill.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel

    private lateinit var prefSettings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        prefSettings = this.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
        editor = prefSettings.edit()

        navController = findNavController(R.id.nav_host_fragment)
        init()
        bottomBarVisibility()
        initBottomNav()
    }
    private fun init() {

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MainViewModel::class.java]
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
                R.id.splashFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.viewPagerFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout ->{
                AlertDialog
                    .Builder(this)
                    .setMessage("Выйти?")
                    .setPositiveButton("Да") { dialogInterface, i ->

                        viewModel.deleteSession()
                        editor.clear().commit()
                        navController.popBackStack(R.id.loginFragment, true )
                        navController.navigate(R.id.loginFragment)

                    }
                    .setNegativeButton("Нет") { dialogInterface, i -> }
                    .create()
                    .show()
            }
        }
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun initBottomNav() {
        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.bottomNavigation.setupWithNavController(navController)
    }




}