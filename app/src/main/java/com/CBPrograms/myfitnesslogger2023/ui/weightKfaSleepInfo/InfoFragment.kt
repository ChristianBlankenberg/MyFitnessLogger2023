package com.CBPrograms.myfitnesslogger2023.ui.weightKfaSleepInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.CBPrograms.myfitnesslogger2023.R
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentInfoBinding
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentSleepDurationBinding
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentWeightKfaBinding
import com.CBPrograms.myfitnesslogger2023.enumerations.informationType
import com.CBPrograms.myfitnesslogger2023.ui.activity.TabulatorChildFragment

class InfoFragment : TabulatorChildFragment() {

    private lateinit var infoViewModel: InfoViewModel
    private var binding: FragmentInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val xbinding get() = binding!!

    override fun GetTitle(): String {
        return contextRef?.resources?.getText(R.string.info).toString()
    }

    override fun sendPreAction() {
        xbinding.infoTextInput.clearFocus()
    }

    override fun sendAction() {
        infoViewModel.sendInformation(xbinding.infoTextInput.text.toString(), activity)
    }

    override fun initializeUI() {
        // initialize fragment controls
        this.initialize(infoViewModel, xbinding.SendButton, xbinding.circularProgress)
        xbinding.infoTextInput.setText("")
    }

    override fun initializeFlows() {
        this.observeTodaysStringFlowAndSetControls(
            infoViewModel.getPastInformationFlow(informationType.information, false, 0, activity),
            R.string.info,
            this@InfoFragment.binding?.infoLabel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        infoViewModel =
            ViewModelProvider(this).get(InfoViewModel::class.java)
        infoViewModel.initialize(this.requireActivity());

        binding = FragmentInfoBinding.inflate(inflater, container, false)
        val root: View = xbinding.root

        return root
    }
}