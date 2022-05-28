package com.hedgehog.androidmaterialdesign.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hedgehog.androidmaterialdesign.R
import com.hedgehog.androidmaterialdesign.databinding.FragmentPictureOfTheDayBinding
import com.hedgehog.androidmaterialdesign.domain.NasaRepositoryImplementation
import com.hedgehog.androidmaterialdesign.view_models.PictureOfTheDayModel
import com.hedgehog.androidmaterialdesign.view_models.MainViewModelFactory

class PictureOfTheDayFragment : Fragment(R.layout.fragment_picture_of_the_day) {

    private val viewModel: PictureOfTheDayModel by viewModels {
        MainViewModelFactory(NasaRepositoryImplementation())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.requestPictureOfTheDay()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPictureOfTheDayBinding.bind(view)

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

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
            viewModel.image.collect { url ->
                url?.let {
                    binding.pictureOfTheDayImg.load(it)
                }
            }
            val bottomSheetFragment =
                BottomSheetBehavior.from(binding.includeBottomSheet.bottomSheetContainer)
            bottomSheetFragment.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.title.collect {
                binding.titleTv.text = it
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.explanation.collect {
                binding.includeBottomSheet.explanationTv.text = it
            }
        }
    }
}