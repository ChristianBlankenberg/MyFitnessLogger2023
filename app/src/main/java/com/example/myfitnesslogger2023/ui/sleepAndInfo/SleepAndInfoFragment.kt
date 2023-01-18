package com.example.myfitnesslogger2023.ui.sleepAndInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myfitnesslogger2023.R
import com.example.myfitnesslogger2023.databinding.FragmentSleepAndInfoBinding
import com.example.myfitnesslogger2023.ui.baseClasses.SendInfoBaseFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SleepAndInfoFragment : SendInfoBaseFragment() {

    private lateinit var sleepAndInfoViewModel: SleepAndInfoViewModel
    private var _binding: FragmentSleepAndInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sleepAndInfoViewModel = ViewModelProvider(this).get(SleepAndInfoViewModel::class.java)

        _binding = FragmentSleepAndInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        this.initialize(sleepAndInfoViewModel, binding.SendButton, binding.circularProgress)

        this.initializeNumberPickers()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeNumberPickers() {

    }

    override fun reInitializeLabels() {
   }

    override fun sendPreAction() {
    }


    override fun sendAction() {
   }

}