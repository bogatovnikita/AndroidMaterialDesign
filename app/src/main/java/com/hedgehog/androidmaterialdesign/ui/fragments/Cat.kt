package com.hedgehog.androidmaterialdesign.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hedgehog.androidmaterialdesign.R
import com.hedgehog.androidmaterialdesign.databinding.FragmentCatBinding

class Cat : Fragment(R.layout.fragment_cat) {
    private var isExpanded = false

    private val binding: FragmentCatBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageScaling()
    }

    private fun imageScaling() {
        binding.imageCat.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                binding.container, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )

            val params: ViewGroup.LayoutParams = binding.imageCat.layoutParams
            params.height =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else
                    ViewGroup.LayoutParams.WRAP_CONTENT
            binding.imageCat.layoutParams = params
            binding.imageCat.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else
                    ImageView.ScaleType.FIT_CENTER
        }
    }
}