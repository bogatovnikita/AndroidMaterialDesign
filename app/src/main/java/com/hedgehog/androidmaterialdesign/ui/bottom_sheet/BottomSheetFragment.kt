package com.hedgehog.androidmaterialdesign.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hedgehog.androidmaterialdesign.databinding.FragmentBottomSheetBinding
import com.hedgehog.androidmaterialdesign.domain.NasaRepositoryImplementation
import com.hedgehog.androidmaterialdesign.view_models.PictureOfTheDayModel
import com.hedgehog.androidmaterialdesign.view_models.MainViewModelFactory

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureOfTheDayModel by viewModels {
        MainViewModelFactory(NasaRepositoryImplementation())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    companion object {
        const val TAG = "BottomSheetMainFragment"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}