package com.example.myfitnesslogger2023.ui.activity

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.example.myfitnesslogger2023.R
import com.example.myfitnesslogger2023.databinding.FragmentActivityBinding
import com.example.myfitnesslogger2023.databinding.FragmentSleepAndInfoBinding
import com.example.myfitnesslogger2023.ui.baseClasses.SendInfoBaseFragment
import com.example.myfitnesslogger2023.ui.sleepAndInfo.SleepAndInfoViewModel
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

        this.initialize(activityViewModel, binding.SendButton, binding.circularProgress)

        this.initializeCheckBoxes()

        return root
    }

    private fun initializeCheckBoxes() {
        binding.joggingCB.setOnClickListener {
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
        }
    }

    private fun onCBChecked(cb: MaterialCheckBox?) {
        if (cb != binding.joggingCB)
        {
            binding.joggingCB.isChecked = false;
        }
        if (cb != binding.cyclingCB)
        {
            binding.cyclingCB.isChecked = false;
        }
        if (cb != binding.hikingCB)
        {
            binding.hikingCB.isChecked = false;
        }
        if (cb != binding.otherCB)
        {
            binding.otherCB.isChecked = false;
        }
    }

}