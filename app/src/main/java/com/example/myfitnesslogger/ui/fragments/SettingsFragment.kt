package com.example.myfitnesslogger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myfitnesslogger.businesslogic.infoType
import com.example.myfitnesslogger.databinding.SettingsFragmentBinding
import com.example.myfitnesslogger.services.keyboardService
import com.example.myfitnesslogger.ui.viewmodels.SettingsViewModel

class SettingsFragment : BaseFragment() {

    private lateinit var viewModel: SettingsViewModel
    private var _binding: SettingsFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        this.initialize(viewModel)

        _binding = SettingsFragmentBinding.inflate(inflater, container, false)

        binding.trainingSaveDataButton.setOnClickListener {
            keyboardService.hideKeyboard()
            this.saveData(this.binding.trainingURLTextField.text.toString(), infoType.training)
        }

        binding.fastingSaveDataButton.setOnClickListener {
            keyboardService.hideKeyboard()
            this.saveData(this.binding.fastingURLTextField.text.toString(), infoType.fasting)
        }

        binding.stepsSaveDataButton.setOnClickListener {
            keyboardService.hideKeyboard()
            this.saveData(this.binding.stepsURLTextField.text.toString(), infoType.steps)
        }

        this.updateUI()

        val root: View = binding.root

        return root
    }

    private fun updateUI()
    {
        binding.trainingURLTextField.setText(this.getUrl(infoType.training))
        binding.fastingURLTextField.setText(this.getUrl(infoType.fasting))
        binding.stepsURLTextField.setText(this.getUrl(infoType.steps))
    }

    private fun saveData(url : String, type : infoType) {
        if (url != "")
        {
            saveUrl(url, type)
        }
        else
        {
            showYesNoDialog( "Delete URL (".plus(type).plus( ")?")) { deleteUrl(type) }
        }
    }

    private fun deleteUrl(type: infoType)
    {
        saveUrl("", type)
    }

    private fun saveUrl(url : String, type: infoType)
    {
        this.viewModel.SetUrl(this.activity, url, type)
    }

    private fun getUrl(type: infoType) : String
    {
        return this.viewModel.GetUrl(type)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}