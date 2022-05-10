package com.hedgehog.androidmaterialdesign.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hedgehog.androidmaterialdesign.databinding.FragmentBottomSheetBinding
import com.hedgehog.androidmaterialdesign.domain.NasaRepositoryImplementation

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
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