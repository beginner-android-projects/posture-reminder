package com.puntogris.posture.ui.settings

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.puntogris.posture.R
import com.puntogris.posture.databinding.BottomSheetSettingsBinding
import com.puntogris.posture.model.SettingItem
import com.puntogris.posture.model.SettingUi
import com.puntogris.posture.ui.base.BaseBottomSheetFragment
import com.puntogris.posture.utils.isIgnoringBatteryOptimizations
import com.puntogris.posture.utils.launchWebBrowserIntent
import com.puntogris.posture.utils.showSnackBarInBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsBottomSheet : BaseBottomSheetFragment<BottomSheetSettingsBinding>(R.layout.bottom_sheet_settings, true) {

    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var settingsAdapter: SettingsAdapter

    override fun initializeViews() {
        binding.bottomSheet = this
        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter(){
        settingsAdapter = SettingsAdapter{ onSettingClicked(it) }
        binding.recyclerView.adapter = settingsAdapter
        setBatteryOptimizationSummary()
        setThemeName()
       // setUserName()
    }

    private fun setUserName(){
        lifecycleScope.launch {
            viewModel.getUserFlow().collect {
                settingsAdapter.updateUserName(it.name)
            }
        }
    }

    private fun setBatteryOptimizationSummary(){
        settingsAdapter.updateBatteryOptimization(isIgnoringBatteryOptimizations())
    }

    private fun setThemeName(){
        val themeNames = resources.getStringArray(R.array.theme_names)
        lifecycleScope.launch {
            viewModel.getThemeNamePosition().collect {
                settingsAdapter.updateThemeName(themeNames[it])
            }
        }
    }

    private fun onSettingClicked(settingItem: SettingItem){
        when(settingItem.code){
            SettingUi.BatteryOpt -> onBatteryClicked()
            SettingUi.Github -> onGithubClicked()
            SettingUi.Help -> onHelpClicked()
            SettingUi.Name -> onNameClicked()
            SettingUi.RateApp -> onRateAppClicked()
            SettingUi.Theme -> onThemeClicked()
            SettingUi.Ticket -> onTicketClicked()
            SettingUi.Version -> onVersionClicked()
            SettingUi.Website -> onWebsiteClicked()
            SettingUi.LogOut -> onLogOutClicked()
        }
    }

    private fun onBatteryClicked(){
        findNavController().navigate(R.id.batteryOptimizationFragment)
    }

    private fun onNameClicked(){
        findNavController().navigate(R.id.editUserNameDialog)
    }
    private fun onRateAppClicked(){
        launchWebBrowserIntent(
            "https://play.google.com/store/apps/details?id=com.puntogris.posture",
            "com.puntogris.posture")
    }
    private fun onThemeClicked(){
        findNavController().navigate(R.id.selectThemeDialog)
    }
    private fun onTicketClicked(){
        findNavController().navigate(R.id.action_settingsBottomSheet_to_ticketBottomSheet)

    }
    private fun onVersionClicked(){
        viewModel.setPandaAnimationPref(true)
        showSnackBarInBottomSheet(R.string.panda_unlocked_message)
    }
    private fun onWebsiteClicked(){
        launchWebBrowserIntent("https://www.postureapp.puntogris.com")
    }
    private fun onGithubClicked(){
        launchWebBrowserIntent("https://www.github.com/puntogris")
    }
    private fun onHelpClicked(){
        launchWebBrowserIntent("https://www.postureapp.puntogris.com/help")
    }
    private fun onLogOutClicked(){
        viewModel.logOut()
    }
}

