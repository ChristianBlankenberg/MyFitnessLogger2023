package com.example.myfitnesslogger2023.ui.activity

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfitnesslogger2023.R
import com.example.myfitnesslogger2023.databinding.FragmentActivityBinding
import com.example.myfitnesslogger2023.services.FirebaseAnalyticsService
import com.example.myfitnesslogger2023.ui.baseClasses.BaseFragment
import com.example.myfitnesslogger2023.ui.baseClasses.SendInfoBaseFragment
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.firebase.analytics.FirebaseAnalytics

class ActivityFragment : BaseFragment() {

    private lateinit var activityViewModel: ActivityViewModel
    private var _binding: FragmentActivityBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ActivityFragment()
    }

    private lateinit var viewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activityViewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)

        _binding = FragmentActivityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        this.initializeTabulatorViewControls()

        return root
    }

    private fun initializeTabulatorViewControls()
    {
        val myFragmentPagerAdapter = TabulatorFragmentPageAdapter(requireContext(), childFragmentManager)

        val context = requireContext()
        myFragmentPagerAdapter.addFragment(JoggingFragment(), context)
        myFragmentPagerAdapter.addFragment(CyclingFragment(), context)
        myFragmentPagerAdapter.addFragment(HikingFragment(), context)
        myFragmentPagerAdapter.addFragment(WorkoutFragment(), context)

        binding.activityTypeTabs.setupWithViewPager(binding.activitiesView)
        binding.activitiesView.adapter = myFragmentPagerAdapter
    }
}