package com.puntogris.posture.ui.settings

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.puntogris.posture.R
import com.puntogris.posture.databinding.DialogEditUsernameBinding
import kotlinx.coroutines.launch

class DialogEditUsername: DialogFragment(){

    private lateinit var binding: DialogEditUsernameBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_edit_username, null, false)
        binding.dialog = this
        return MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_rounded)
            .setView(binding.root)
            .create()
    }

    fun onSaveName(){
        val text = binding.editTextTextPersonName.text.toString()
        lifecycleScope.launch {
          //  viewModel.updateUserName(text)
        }
    }
}