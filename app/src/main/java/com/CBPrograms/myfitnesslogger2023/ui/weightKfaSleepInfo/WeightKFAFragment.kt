package com.CBPrograms.myfitnesslogger2023.ui.weightKfaSleepInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.CBPrograms.myfitnesslogger2023.R
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentWeightKfaBinding
import com.CBPrograms.myfitnesslogger2023.enumerations.informationType
import com.CBPrograms.myfitnesslogger2023.ui.activity.TabulatorChildFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WeightKFAFragment : TabulatorChildFragment() {

    private lateinit var weightKFAViewModel: WeightKFAViewModel
    private var binding: FragmentWeightKfaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val xbinding get() = binding!!

    override fun GetTitle(): String {
        return contextRef?.resources?.getText(R.string.weight_and_kfa).toString()
    }

    override fun sendPreAction() {
        xbinding.weightGreat.clearFocus()
        xbinding.weightSmall.clearFocus()
        xbinding.kfaGreat.clearFocus()
        xbinding.kfaSmall.clearFocus()
    }

    override fun sendAction() {
        weightKFAViewModel.sendData(
            xbinding.weightGreat.value,
            xbinding.weightSmall.value,
            xbinding.kfaGreat.value,
            xbinding.kfaSmall.value,
            xbinding.kfaSmall.isVisible,
            activity)
    }

    override fun initializeUI() {
        // initialize fragment controls
        this.initialize(weightKFAViewModel, xbinding.SendButton, xbinding.circularProgress)

        this.initializeNumberPickers()
    }

    override fun initializeFlows() {
        this.observeTodaysYesterDaysDoubleFlowsAndSetControls(
            weightKFAViewModel.getPastInformationFlow(informationType.weight, false,0, activity),
            weightKFAViewModel.getPastInformationFlow(informationType.weight, false, -1, activity),
            R.string.weight,
            this@WeightKFAFragment.binding?.weightLabel,
            this@WeightKFAFragment.binding?.weightGreat,
            this@WeightKFAFragment.binding?.weightSmall
        )

        this.observeTodaysYesterDaysDoubleFlowsAndSetControls(
            weightKFAViewModel.getPastInformationFlow(informationType.kfa, false,0, activity),
            weightKFAViewModel.getPastInformationFlow(informationType.kfa, false, -1, activity),
            R.string.kfa,
            this@WeightKFAFragment.binding?.kfaLabel,
            this@WeightKFAFragment.binding?.kfaGreat,
            this@WeightKFAFragment.binding?.kfaSmall
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weightKFAViewModel =
            ViewModelProvider(this).get(WeightKFAViewModel::class.java)
        weightKFAViewModel.initialize(this.requireActivity());

        binding = FragmentWeightKfaBinding.inflate(inflater, container, false)
        val root: View = xbinding.root

        return root
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
    }

}