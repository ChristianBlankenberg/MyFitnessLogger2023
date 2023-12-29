package com.example.myfitnesslogger.ui.fragments

import android.view.View
import com.example.myfitnesslogger.databinding.InfosFragmentBinding
import com.example.myfitnesslogger.services.keyboardService
import com.example.myfitnesslogger.ui.viewmodels.NotesViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotesSubFragment(val binding: InfosFragmentBinding, val host : BaseFragment, val viewModel: NotesViewModel) : BaseFragment() {

    fun updateUI() {
        this.binding.remarkedComment.text = this.viewModel.getCachedComment()
    }

    fun initializeUI() {
        binding.AddButton.setOnClickListener {
            val remarks = binding.remarkComment.text.toString()
            if (remarks != "") {
                keyboardService.hideKeyboard()
                sendData(
                    binding.remarkComment.text.toString(),
                    binding.bergaCB.isChecked,
                    binding.noTrainingCB.isChecked
                )
            } else {
                showYesNoDialog("Remove comment?") {
                    sendData(
                        "- deleted -",
                        binding.bergaCB.isChecked,
                        binding.noTrainingCB.isChecked
                    )
                }
            }

            host.activity?.runOnUiThread {
                this.updateUI()
                binding.circularGetProgress.visibility = View.INVISIBLE
            }
        }

        binding.GetButton.setOnClickListener {
            GlobalScope.launch {
                host.activity?.runOnUiThread {
                    binding.circularGetProgress.visibility = View.VISIBLE
                }

                val comment = viewModel.getComment()

                host.activity?.runOnUiThread {
                    binding.remarkedComment.setText(comment)
                    binding.circularGetProgress.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun sendData(text : String, inBerga : Boolean, noTraining : Boolean)
    {
        GlobalScope.launch {
            host.activity?.runOnUiThread {
                binding.circularSendProgress.visibility = View.VISIBLE
            }

            viewModel.sendComment(text, inBerga, noTraining)

            host.activity?.runOnUiThread {
                this@NotesSubFragment.updateUI()
                binding.circularSendProgress.visibility = View.INVISIBLE
            }
        }
    }
}