package com.example.myfitnesslogger2023.ui.activity

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfitnesslogger2023.R
import com.example.myfitnesslogger2023.databinding.FragmentActivityBinding
import com.example.myfitnesslogger2023.ui.baseClasses.SendInfoBaseFragment
import com.google.android.material.checkbox.MaterialCheckBox

class ActivityFragment : SendInfoBaseFragment() {

    private lateinit var activityViewModel: ActivityViewModel
    private var _binding: FragmentActivityBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ActivityFragment()
    }

    private lateinit var viewModel: ActivityViewModel
    override fun sendPreAction() {
        val selectedTabPosition = binding.activityTypeTabs.selectedTabPosition
    }

    override fun sendAction() {
    }

    override fun reInitializeLabels() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activityViewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)

        _binding = FragmentActivityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        this.initialize(activityViewModel,  binding.SendButton, binding.circularProgress)

        this.initializeTabulatorViewControls()
        this.initializeCheckBoxes()
        this.checkPanelVisibilties()
        this.initializeCaloriesNumberPickers()
        this.initializeDurationNumberPickers()

        return root
    }

    private fun initializeTabulatorViewControls()
    {
        val myFragmentPagerAdapter = TabulatorFragmentPageAdapter(requireContext(), childFragmentManager)
        myFragmentPagerAdapter.addFragment(JoggingFragment(requireContext()))
        myFragmentPagerAdapter.addFragment(CyclingFragment(requireContext()))
        myFragmentPagerAdapter.addFragment(HikingFragment(requireContext()))
        myFragmentPagerAdapter.addFragment(WorkoutFragment(requireContext()))

        binding.activityTypeTabs.setupWithViewPager(binding.activitiesView)
        binding.activitiesView.adapter = myFragmentPagerAdapter
    }

    private fun initializeCheckBoxes() {
/*        binding.joggingCB.setOnClickListener {
            this.onCBChecked(it as MaterialCheckBox)
        }
        binding.cyclingCB.setOnClickListener {
            this.onCBChecked(it as MaterialCheckBox)
        }
        binding.hikingCB.setOnClickListener {
            this.onCBChecked(it as MaterialCheckBox)
        }
        binding.otherCB.setOnClickListener {
            this.onCBChecked(it as MaterialCheckBox)
        }*/
    }

    private fun onCBChecked(cb: MaterialCheckBox?) {
/*        if (cb != binding.joggingCB) {
            binding.joggingCB.isChecked = false;
        }
        if (cb != binding.cyclingCB) {
            binding.cyclingCB.isChecked = false;
        }
        if (cb != binding.hikingCB) {
            binding.hikingCB.isChecked = false;
        }
        if (cb != binding.otherCB) {
            binding.otherCB.isChecked = false;
        }*/

        checkPanelVisibilties()
    }

    private fun checkPanelVisibilties()
    {
/*        binding.joggingPanel.isVisible = binding.joggingCB.isChecked
        binding.cyclingCBPanel.isVisible = binding.cyclingCB.isChecked
        binding.hikingCBPanel.isVisible = binding.hikingCB.isChecked
        binding.otherCBPanel.isVisible = binding.otherCB.isChecked*/
    }

    private fun initializeCaloriesNumberPickers()
    {
       /* binding.joggingCalories.minValue = 1
        binding.joggingCalories.maxValue = 3000
        binding.joggingCalories.value = 100

        binding.cyclingCalories.minValue = 1
        binding.cyclingCalories.maxValue = 3000
        binding.cyclingCalories.value = 100

        binding.hikingCalories.minValue = 1
        binding.hikingCalories.maxValue = 3000
        binding.hikingCalories.value = 100

        binding.otherCalories.minValue = 1
        binding.otherCalories.maxValue = 3000
        binding.otherCalories.value = 100*/
    }

    private fun initializeDurationNumberPickers()
    {
/*
        initializeDurationNumberPickers(binding.joggingDurationHours, binding.joggingDurationMinutes)
        initializeDurationNumberPickers(binding.cyclingDurationHours, binding.cyclingDurationMinutes)
        initializeDurationNumberPickers(binding.hikingDurationHours, binding.hikingDurationMinutes)
        initializeDurationNumberPickers(binding.otherDurationHours, binding.otherDurationMinutes)
*/
    }
}