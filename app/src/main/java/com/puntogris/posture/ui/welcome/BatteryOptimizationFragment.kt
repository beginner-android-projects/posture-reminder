package com.puntogris.posture.ui.welcome

import androidx.navigation.fragment.findNavController
import com.puntogris.posture.databinding.FragmentBatteryOptimizationBinding
import com.puntogris.posture.ui.base.BaseFragment
import android.content.Intent
import android.os.PowerManager
import android.provider.Settings
import com.puntogris.posture.utils.gone
import android.text.Spannable
import android.text.style.TypefaceSpan
import android.text.SpannableString
import android.graphics.Typeface
import android.text.Html
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import com.puntogris.posture.R
import com.puntogris.posture.utils.isIgnoringBatteryOptimizations

class BatteryOptimizationFragment : BaseFragment<FragmentBatteryOptimizationBinding>(R.layout.fragment_battery_optimization) {

    override fun initializeViews() {
        binding.fragment = this
        checkPowerStatus()

        val stepOne =
            "<font color='#757575'>1 - Busca </font> " +
            "<b><font color='#000000'>recordatorio de postura</font></b>" +
            "<font color='#757575'> en el listado</font>"

        val stepTwo =
            "<font color='#757575'>2 - Selecciona </font> " +
            "<b><font color='#000000'> no optimizar</font></b>" +
            "<font color='#757575'> la bateria</font>"


        binding.stepOne.text = HtmlCompat.fromHtml(stepOne, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.stepTwo.text = HtmlCompat.fromHtml(stepTwo, HtmlCompat.FROM_HTML_MODE_LEGACY)

    }

    private fun checkPowerStatus(){
        if (isIgnoringBatteryOptimizations()){
            binding.apply {
                powerManagerState.text = "Todo en orden"
                powerStateImage.setImageResource(R.drawable.ic_baseline_check_circle_24)
                requireOptimizationGroup.gone()
            }
        }
    }

    fun openBatteryOptimization(){
        startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
    }

    fun onFinalizeButtonClicked(){
        findNavController().navigate(R.id.action_batteryOptimizationFragment_to_mainFragment)
    }

    override fun onResume() {
        checkPowerStatus()
        super.onResume()
    }
}