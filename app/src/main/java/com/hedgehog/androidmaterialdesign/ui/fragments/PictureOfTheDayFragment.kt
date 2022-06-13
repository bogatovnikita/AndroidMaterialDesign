package com.hedgehog.androidmaterialdesign.ui.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.transition.*
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.hedgehog.androidmaterialdesign.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hedgehog.androidmaterialdesign.databinding.FragmentPictureOfTheDayBinding
import com.hedgehog.androidmaterialdesign.domain.NasaRepositoryImplementation
import com.hedgehog.androidmaterialdesign.view_models.MainViewModelFactory
import com.hedgehog.androidmaterialdesign.view_models.PictureOfTheDayModel


class PictureOfTheDayFragment : Fragment(R.layout.fragment_picture_of_the_day) {
    private var isExpanded = false

    private val binding: FragmentPictureOfTheDayBinding by viewBinding()

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
        TransitionManager.beginDelayedTransition(
            binding.pictureOfTheDayContainer,
            Slide(Gravity.END)
        )
        setupFragment()
        initClickFAB()
        imageScaling()
    }

    private fun imageScaling() {
        binding.pictureOfTheDayImg.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                binding.pictureOfTheDayContainer, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )

            val params: ViewGroup.LayoutParams = binding.pictureOfTheDayImg.layoutParams
            params.height =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else
                    ViewGroup.LayoutParams.WRAP_CONTENT
            binding.pictureOfTheDayImg.layoutParams = params
            binding.pictureOfTheDayImg.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else
                    ImageView.ScaleType.FIT_CENTER
        }
    }


    private fun initClickFAB() {
        var flagButtonFab = true
        val standardBottomSheetBehavior =
            BottomSheetBehavior.from(binding.includeBottomSheet.bottomSheetContainer)

        binding.fabButton.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(requireActivity(),R.anim.shake)
            anim.duration = 200L
            binding.fabButton.startAnimation(anim)
            if (flagButtonFab) {
                standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                flagButtonFab = false
            } else {
                standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                flagButtonFab = true

            }
        }
    }

    private fun setupFragment() {
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