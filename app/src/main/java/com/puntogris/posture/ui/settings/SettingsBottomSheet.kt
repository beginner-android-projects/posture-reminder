package com.puntogris.posture.ui.settings

import android.app.Dialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.puntogris.posture.R
import com.puntogris.posture.databinding.BottomSheetSettingsBinding
import com.puntogris.posture.ui.base.BaseBottomSheetFragment
import com.puntogris.posture.utils.isIgnoringBatteryOptimizations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsBottomSheet : BaseBottomSheetFragment<BottomSheetSettingsBinding>(R.layout.bottom_sheet_settings, true) {

    private val viewModel: SettingsViewModel by viewModels()

    override fun initializeViews() {
        binding.bottomSheet = this
        binding.notificationSwitch.itemName.text = "Habilitar notificaciones"
        binding.theme.itemName.text = "Tema"
        binding.theme.itemDescription.text = "Claro"
        binding.username.itemName.text = "Nombre"
        binding.username.itemDescription.text = "Joaquin"
        binding.username.layout.setOnClickListener {
            val action = SettingsBottomSheetDirections.actionSettingsBottomSheetToDialogName("")
            findNavController().navigate(action)
        }
        binding.theme.layout.setOnClickListener {
            findNavController().navigate(R.id.dialogTheme)
        }

        binding.batteryOptimization.apply {
            itemName.text = "Optimiz. bateria"
            itemDescription.text = if(isIgnoringBatteryOptimizations()) "Correcto" else "Necesita accion"
            layout.setOnClickListener {
                findNavController().navigate(R.id.batteryOptimizationFragment)
            }
        }


    }
}