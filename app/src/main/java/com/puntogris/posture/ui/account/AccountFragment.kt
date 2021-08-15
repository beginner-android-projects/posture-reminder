package com.puntogris.posture.ui.account

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentAccountBinding
import com.puntogris.posture.model.DayHistory
import com.puntogris.posture.ui.base.BaseFragment
import com.puntogris.posture.utils.capitalizeFirstChar
import com.puntogris.posture.utils.getDayStringFormatted
import com.puntogris.posture.utils.showItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(R.layout.fragment_account) {

    private val viewModel: AccountViewModel by viewModels()

    override fun initializeViews() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.selectReminder.itemName.text = "Recordatorio activo"
        binding.selectReminder.itemDescription.text = "Recordatorio1"
        binding.selectReminder.layout.setOnClickListener {
            findNavController().navigate(R.id.manageRemindersBottomSheet)
        }

        lifecycleScope.launch {
            val data = viewModel.getWeekData()
            val barSet = lastWeekLabels(data)
            binding.barChart.animate(barSet)
        }
    }


    private fun lastWeekLabels(data: List<DayHistory>): List<Pair<String, Float>>{
        val today = LocalDate.now()
        val labels = mutableListOf<Pair<String, Float>>()

        for (i in 6 downTo 0L){
            val day = today.minusDays(i)
            val dayString = if (i == 0L) "Hoy" else day.getDayStringFormatted()

            val roomEntry = data.singleOrNull{ it.date == day.toString()}
            val value = roomEntry?.expGained?.toFloat() ?: 0F
            labels.add(dayString to value)
        }
        return labels
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.showItem(R.id.settings)
        menu.showItem(R.id.newReminder)
        super.onCreateOptionsMenu(menu, inflater)
    }

}