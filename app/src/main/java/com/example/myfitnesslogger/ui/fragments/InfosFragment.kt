package com.example.myfitnesslogger.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfitnesslogger.R
import com.example.myfitnesslogger.businesslogic.infoType
import com.example.myfitnesslogger.databinding.InfosFragmentBinding
import com.example.myfitnesslogger.services.keyboardService
import com.example.myfitnesslogger.ui.viewmodels.FastingViewModel
import com.example.myfitnesslogger.ui.viewmodels.InfosViewModel
import com.example.myfitnesslogger.ui.viewmodels.NotesViewModel
import com.example.myfitnesslogger.ui.viewmodels.StepsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InfosFragment : BaseFragment() {

    companion object {
        fun newInstance() = InfosFragment()
    }

    private lateinit var notesSubFragment : NotesSubFragment
    private lateinit var fastingSubFragment : FastingSubFragment
    private lateinit var stepsSubFragment : StepsSubFragment

    private var infotype : infoType = infoType.training
    private lateinit var viewModel: InfosViewModel
    private lateinit var binding: InfosFragmentBinding

    override fun onResume() {
        super.onResume()
        //this.binding.GetButton.isEnabled = this.isConnectionUrlDefined()
        this.binding.AddButton.isEnabled = this.isConnectionUrlDefined(infoType.training)

        this.updateUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = InfosFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(InfosViewModel::class.java)

        this.initialize(viewModel)

        this.initialiueUI()
        this.updateUI()

        return binding.root
    }

    private fun initialiueUI() {
         fun selectUI(type : infoType) {
            infotype = type
            updateUI()
        }

        var notesViewModel = NotesViewModel()
        notesViewModel.initialize(this.requireActivity())
        notesSubFragment = NotesSubFragment(this.binding, this, notesViewModel)
        this.notesSubFragment.initializeUI()

        var fastingViewModel = FastingViewModel()
        fastingViewModel.initialize(this.requireActivity())
        fastingSubFragment = FastingSubFragment(this.binding, this, fastingViewModel)
        this.fastingSubFragment.initializeUI()

        var stepsViewModel = StepsViewModel()
        stepsViewModel.initialize(this.requireActivity())
        stepsSubFragment = StepsSubFragment(this.binding, this, stepsViewModel)
        this.stepsSubFragment.initializeUI()

        binding.stepsButton.setOnClickListener {
            selectUI(infoType.steps)
        }
        binding.fastingButton.setOnClickListener {
            selectUI(infoType.fasting)
        }
        binding.remarksButton.setOnClickListener {
            selectUI(infoType.training)
        }
    }

    private fun updateUI() {
        fun envelope(content : String) : String
        {
            return  "-> ".plus(content)
        }

        when (infotype)
        {
            infoType.training ->
            {
                this.binding.remarksButton.text = envelope(resources.getText(R.string.remarksButton).toString().uppercase())
                this.binding.fastingButton.text = resources.getText(R.string.fastingButton).toString()
                this.binding.stepsButton.text = resources.getText(R.string.stepsButton).toString()
            }
            infoType.fasting ->
            {
                this.binding.remarksButton.text = resources.getText(R.string.remarksButton).toString()
                this.binding.fastingButton.text = envelope(resources.getText(R.string.fastingButton).toString().uppercase())
                this.binding.stepsButton.text = resources.getText(R.string.stepsButton).toString()
            }
            infoType.steps ->
            {
                this.binding.remarksButton.text = resources.getText(R.string.remarksButton).toString()
                this.binding.fastingButton.text = resources.getText(R.string.fastingButton).toString()
                this.binding.stepsButton.text = envelope(resources.getText(R.string.stepsButton).toString().uppercase())
            }
        }

        binding.notesPanel.visibility = if (infotype == infoType.training) View.VISIBLE else View.GONE
        binding.fastingPanel.visibility = if (infotype == infoType.fasting) View.VISIBLE else View.GONE
        binding.stepsPanel.visibility = if (infotype == infoType.steps) View.VISIBLE else View.GONE

        this.notesSubFragment.updateUI()
        this.fastingSubFragment.updateUI()
        this.stepsSubFragment.updateUI()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InfosViewModel::class.java)
        // TODO: Use the ViewModel
    }
}