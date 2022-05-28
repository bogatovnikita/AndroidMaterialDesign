package com.hedgehog.androidmaterialdesign.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.hedgehog.androidmaterialdesign.R
import com.hedgehog.androidmaterialdesign.databinding.FragmentEarthPictureBinding
import com.hedgehog.androidmaterialdesign.domain.earth.EarthPictureRepositoryImplementation
import com.hedgehog.androidmaterialdesign.view_models.EarthPictureFactory
import com.hedgehog.androidmaterialdesign.view_models.EarthPictureViewModel

class EarthPictureFragment : Fragment(R.layout.fragment_earth_picture) {

    private val viewModel: EarthPictureViewModel by viewModels {
        EarthPictureFactory(EarthPictureRepositoryImplementation())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.requestEarthPicture()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentEarthPictureBinding.bind(view)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.loading.collect {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.error.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.image.collect {
                binding.earthPictureImage.load(it)
            }
        }
    }
}