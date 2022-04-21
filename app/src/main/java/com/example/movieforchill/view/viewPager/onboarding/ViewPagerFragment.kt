package com.example.movieforchill.view.viewPager.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.movieforchill.R
import com.example.movieforchill.view.viewPager.onboarding.ViewPagerAdapter
import com.example.movieforchill.view.viewPager.onboarding.screens.FirstScreen
import com.example.movieforchill.view.viewPager.onboarding.screens.SecondScreen
import com.example.movieforchill.view.viewPager.onboarding.screens.ThirdScreen

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        view.findViewById<ViewPager2>(R.id.viewPager).adapter = adapter

        return view
    }

}