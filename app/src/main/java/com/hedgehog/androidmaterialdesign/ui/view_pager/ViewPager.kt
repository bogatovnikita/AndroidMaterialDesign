package com.hedgehog.androidmaterialdesign.ui.view_pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hedgehog.androidmaterialdesign.R
import com.hedgehog.androidmaterialdesign.databinding.FragmentViewPagerBinding
import com.hedgehog.androidmaterialdesign.ui.adapters.ViewPagerAdapter
import com.hedgehog.androidmaterialdesign.ui.fragments.PictureOfTheDayFragment

class ViewPager : Fragment(R.layout.fragment_view_pager) {
    private val listFragment = listOf<Fragment>(
        PictureOfTheDayFragment(),
        PictureOfTheDayFragment(),
        PictureOfTheDayFragment()
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentViewPagerBinding.bind(view)
        val adapter = ViewPagerAdapter(requireActivity(), listFragment)
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.setViewPager2(binding.viewPager)
    }
}