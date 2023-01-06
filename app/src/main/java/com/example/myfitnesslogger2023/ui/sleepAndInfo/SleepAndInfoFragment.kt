package com.example.myfitnesslogger2023.ui.sleepAndInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myfitnesslogger2023.R
import com.example.myfitnesslogger2023.databinding.FragmentSleepAndInfoBinding
import com.example.myfitnesslogger2023.ui.baseClasses.BaseFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SleepAndInfoFragment : BaseFragment() {

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

        return root
    }

    private fun reInitializeLabels() {
        GlobalScope.launch {
            val todaysSleepDurationHours = this@SleepAndInfoFragment.getTodaysSleepDurationHours()
            val todaysSleepDurationMinutes = this@SleepAndInfoFragment.getTodaysSleepDurationMinutes()

            activity?.runOnUiThread {
                this@SleepAndInfoFragment.initializeALabel(this@SleepAndInfoFragment.binding.sleepDurationLabel, R.string.sleepDuration, todaysSleepDurationHours + ":" + todaysSleepDurationMinutes )
            }
        }
    }

    private fun getTodaysSleepDurationMinutes(): String {
        return "?"
    }

    private fun getTodaysSleepDurationHours(): String {
        return "?"
    }

    override fun onResume() {
        super.onResume()

        this.reInitializeLabels()
        this.binding.SendButton.isEnabled = this.sleepAndInfoViewModel.canSendData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}