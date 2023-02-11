package com.CBPrograms.myfitnesslogger2023.ui.yesterdaysStepsInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.CBPrograms.myfitnesslogger2023.R
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.SendInfoBaseFragment
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentYesterdaysStepsInfoBinding
import com.CBPrograms.myfitnesslogger2023.enumerations.informationType

class YesterdaysStepsInfoFragment : SendInfoBaseFragment() {

    private lateinit var yesterdaysStepsInfoViewModel: YesterdaysStepsInfoViewModel
    private var binding: FragmentYesterdaysStepsInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val xbinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        yesterdaysStepsInfoViewModel = ViewModelProvider(this).get(YesterdaysStepsInfoViewModel::class.java)

        binding = FragmentYesterdaysStepsInfoBinding.inflate(inflater, container, false)
        val root: View = xbinding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun initializeUI() {
        this.initialize(yesterdaysStepsInfoViewModel, xbinding.SendButton, xbinding.circularProgress)

        xbinding.yesterdaysStepsNP.minValue=0
        xbinding.yesterdaysStepsNP.maxValue=105000
        xbinding.yesterdaysStepsNP.value=12000
    }

    override fun sendPreAction() {
        xbinding.yesterdaysStepsNP.clearFocus()
    }

    override fun sendAction() {
        yesterdaysStepsInfoViewModel.sendYesterdaySteps(
            xbinding.yesterdaysStepsNP.value,
            activity)
    }

    override fun initializeFlows() {
        this.observeYesterdaysIntFlowAndSetControls(
            yesterdaysStepsInfoViewModel.getPastInformationFlow(informationType.steps, false, -1, activity),
            R.string.yesterdaysSteps,
            this@YesterdaysStepsInfoFragment.binding?.yesterdaysStepsLabel,
            this@YesterdaysStepsInfoFragment.binding?.yesterdaysStepsNP,
        )
    }
}