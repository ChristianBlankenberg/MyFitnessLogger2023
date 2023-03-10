package com.CBPrograms.myfitnesslogger2023.ui.weightKfaSleepInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentWeightKfaSleepdurationInfoBinding
import com.CBPrograms.myfitnesslogger2023.ui.activity.*
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.BaseFragment

class WeightKFASleepInfoFragment : BaseFragment() {

    private lateinit var weightKFASleepInfoViewModel: WeightKFASleepInfoViewModel
    private var binding: FragmentWeightKfaSleepdurationInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val xbinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weightKFASleepInfoViewModel =
            ViewModelProvider(this).get(WeightKFASleepInfoViewModel::class.java)
        weightKFASleepInfoViewModel.initialize(this.requireActivity());

        binding = FragmentWeightKfaSleepdurationInfoBinding.inflate(inflater, container, false)
        val root: View = xbinding.root

        this.initializeTabulatorViewControls()

        return root
    }

    private fun initializeTabulatorViewControls()
    {
        val myFragmentPagerAdapter = TabulatorFragmentPageAdapter(requireContext(), childFragmentManager)

        val context = requireContext()
        myFragmentPagerAdapter.addFragment(WeightKFAFragment(), context)
        myFragmentPagerAdapter.addFragment(SleepDurationFragment(), context)
        myFragmentPagerAdapter.addFragment(InfoFragment(), context)

        xbinding.weightKfaSleepInfoTabs.setupWithViewPager(xbinding.weightKfaSleepInfoView)
        xbinding.weightKfaSleepInfoView.adapter = myFragmentPagerAdapter
    }

/*
    override fun initializeUI() {
        // initialize fragment controls
        this.initialize(weightAndKFAViewModel, xbinding.SendButton, xbinding.circularProgress)

        this.initializeNumberPickers()
    }

    override fun initializeFlows() {
        this.observeTodaysYesterDaysDoubleFlowsAndSetControls(
            weightAndKFAViewModel.getPastInformationFlow(informationType.weight, false,0, activity),
            weightAndKFAViewModel.getPastInformationFlow(informationType.weight, false, -1, activity),
            R.string.weight,
            this@WeightKFAFragment.binding?.weightLabel,
            this@WeightKFAFragment.binding?.weightGreat,
            this@WeightKFAFragment.binding?.weightSmall
        )

        this.observeTodaysYesterDaysDoubleFlowsAndSetControls(
            weightAndKFAViewModel.getPastInformationFlow(informationType.kfa, false,0, activity),
            weightAndKFAViewModel.getPastInformationFlow(informationType.kfa, false, -1, activity),
            R.string.kfa,
            this@WeightKFAFragment.binding?.kfaLabel,
            this@WeightKFAFragment.binding?.kfaGreat,
            this@WeightKFAFragment.binding?.kfaSmall
        )

        this.observeTodaysDurationFlowAndSetControls(
            weightAndKFAViewModel.getPastInformationFlow(
                informationType.sleepduration,
                false,
                0,
                activity
            ),
            R.string.sleepDuration,
            this@WeightKFAFragment.binding?.sleepDurationLabel,
            this@WeightKFAFragment.binding?.sleepDurationHours,
            this@WeightKFAFragment.binding?.sleepDurationMinutes
        )

        this.observeTodaysStringFlowAndSetControls(
            weightAndKFAViewModel.getPastInformationFlow(informationType.information, false, 0, activity),
            R.string.info,
            this@WeightKFAFragment.binding?.infoLabel
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun sendPreAction() {
        xbinding.weightGreat.clearFocus()
        xbinding.weightSmall.clearFocus()
        xbinding.kfaGreat.clearFocus()
        xbinding.kfaSmall.clearFocus()
        xbinding.sleepDurationHours.clearFocus()
        xbinding.sleepDurationMinutes.clearFocus()
        xbinding.infoTextInput.clearFocus()
    }

    override fun sendAction() {
        weightAndKFAViewModel.sendData(
            xbinding.weightGreat.value,
            xbinding.weightSmall.value,
            xbinding.kfaGreat.value,
            xbinding.kfaSmall.value,
            xbinding.kfaSmall.isVisible,
            activity)

        weightAndKFAViewModel.sendSleepDuration(
            xbinding.sleepDurationHours.value,
            xbinding.sleepDurationMinutes.value,
            activity)

        weightAndKFAViewModel.sendInformation(xbinding.infoTextInput.text.toString(), activity)
    }

    override fun sendPostAction() {
        initializeFlows()
    }

    private fun initializeNumberPickers() {
        xbinding.weightGreat.minValue = 72
        xbinding.weightGreat.maxValue = 105
        xbinding.weightGreat.value = 78

        xbinding.weightSmall.minValue = 0
        xbinding.weightSmall.maxValue = 9
        xbinding.weightSmall.value = 5

        xbinding.kfaGreat.minValue = 17
        xbinding.kfaGreat.maxValue = 25
        xbinding.kfaGreat.value = 21

        xbinding.kfaSmall.minValue = 0
        xbinding.kfaSmall.maxValue = 9
        xbinding.kfaSmall.value = 5

        initializeDurationNumberPickers(xbinding.sleepDurationHours, xbinding.sleepDurationMinutes)
    }
 */

}