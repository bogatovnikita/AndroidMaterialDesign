package com.hedgehog.androidmaterialdesign.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hedgehog.androidmaterialdesign.R
import com.hedgehog.androidmaterialdesign.databinding.FragmentChooseThemeBinding
import com.hedgehog.androidmaterialdesign.ui.MainActivity
import com.hedgehog.androidmaterialdesign.ui.ThemeDefault
import com.hedgehog.androidmaterialdesign.ui.ThemeGreen
import com.hedgehog.androidmaterialdesign.ui.ThemePink

class ChooseThemeFragment : Fragment(R.layout.fragment_choose_theme) {
    private var _binding: FragmentChooseThemeBinding? = null
    private val binding get() = _binding!!
    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun checkOnTheSelectedTopic() {
        when (parentActivity.getCurrentTheme()) {
            -1 -> {
                binding.radioButtonDefaultTheme.isChecked = true
            }
            0
            -> {
                binding.radioButtonDefaultTheme.isChecked = true
            }
            1 -> {
                binding.radioButtonGreenTheme.isChecked = true
            }
            2 -> {
                binding.radioButtonPinkTheme.isChecked = true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkOnTheSelectedTopic()
        initCheckRadioButtons()
    }

    private fun initCheckRadioButtons() {
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.radioButtonDefaultTheme.id -> {
                    parentActivity.setCurrentTheme(ThemeDefault)
                    parentActivity.recreate()
                }
                binding.radioButtonGreenTheme.id -> {
                    parentActivity.setCurrentTheme(ThemeGreen)
                    parentActivity.recreate()
                }
                binding.radioButtonPinkTheme.id -> {
                    parentActivity.setCurrentTheme(ThemePink)
                    parentActivity.recreate()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}