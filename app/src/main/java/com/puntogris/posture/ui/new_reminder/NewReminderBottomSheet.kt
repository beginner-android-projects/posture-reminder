package com.puntogris.posture.ui.new_reminder

import android.text.InputType
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.maxkeppeler.sheets.color.ColorSheet
import com.maxkeppeler.sheets.core.IconButton
import com.maxkeppeler.sheets.core.SheetStyle
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.type.InputEditText
import com.maxkeppeler.sheets.input.type.InputRadioButtons
import com.maxkeppeler.sheets.options.OptionsSheet
import com.maxkeppeler.sheets.time_clock.ClockTimeSheet
import com.puntogris.posture.R
import com.puntogris.posture.data.OnSaveListener
import com.puntogris.posture.databinding.BottomSheetNewReminderBinding
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.ReminderUi
import com.puntogris.posture.model.RepoResult
import com.puntogris.posture.model.Sound
import com.puntogris.posture.ui.base.BaseBottomSheetFragment
import com.puntogris.posture.utils.Constants.DATA_KEY
import com.puntogris.posture.utils.Constants.INTERVAL_KEY
import com.puntogris.posture.utils.Constants.SOUND_PICKER_KEY
import com.puntogris.posture.utils.Constants.TIME_UNIT_KEY
import com.puntogris.posture.utils.Constants.VIBRATION_PICKER_KEY
import com.puntogris.posture.utils.Utils
import com.puntogris.posture.utils.createSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewReminderBottomSheet : BaseBottomSheetFragment<BottomSheetNewReminderBinding>(
    R.layout.bottom_sheet_new_reminder,
    true
) {

    private val viewModel: NewReminderViewModel by viewModels()
    private val args: NewReminderBottomSheetArgs by navArgs()

    override fun initializeViews() {
        binding.bottomSheet = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        ReminderItemAdapter(requireContext()) { onReminderItemClicked(it) }.let {
            binding.recyclerView.adapter = it
            subscribeUi(it)
        }

        args.reminder?.let {
            viewModel.updateReminder(it)
        }

        setFragmentResultListener(VIBRATION_PICKER_KEY){ _, bundle ->
            viewModel.saveReminderVibrationPattern(bundle.getInt(DATA_KEY))
        }
        setFragmentResultListener(SOUND_PICKER_KEY){ _, bundle ->
            bundle.getParcelable<Sound>(DATA_KEY)?.let {
                viewModel.saveReminderSoundPattern(it)
            }
        }
    }

    private fun subscribeUi(adapter: ReminderItemAdapter) {
        viewModel.reminder.observe(viewLifecycleOwner) {
            adapter.updateConfigData(it)
        }
    }

    fun onSaveReminder() {
        lifecycleScope.launch {
            viewModel.saveReminder().collect {
                when(it){
                    RepoResult.Error -> {}
                    RepoResult.InProgress -> {}
                    RepoResult.Success -> {
                        createSnackBar(getString(R.string.snack_reminder_created_success))
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun onReminderItemClicked(reminderUi: ReminderUi) {
        when (reminderUi) {
            is ReminderUi.Color -> openColorPicker()
            is ReminderUi.Item.Interval -> openIntervalPicker()
            is ReminderUi.Name -> viewModel.saveReminderName(reminderUi.value)
            is ReminderUi.Item.Days -> openDaysPicker()
            is ReminderUi.Item.End -> openTimePicker(reminderUi)
            is ReminderUi.Item.Start -> openTimePicker(reminderUi)
            is ReminderUi.Item.Sound -> onSoundPicker()
            is ReminderUi.Item.Vibration -> onVibrationPicker()
        }
    }

    private fun onSoundPicker(){
        val action = NewReminderBottomSheetDirections
            .actionNewReminderBottomSheetToSoundSelectorDialog(viewModel.reminder.value!!.sound ?: Sound())
        findNavController().navigate(action)    }

    private fun onVibrationPicker(){
        val action = NewReminderBottomSheetDirections
            .actionNewReminderBottomSheetToVibrationSelectorDialog(viewModel.reminder.value!!.vibrationPattern)
        findNavController().navigate(action)
    }

    private fun openColorPicker() {
        ColorSheet().show(requireParentFragment().requireContext()) {
            title(this@NewReminderBottomSheet.getString(R.string.color_picker_title))
            disableSwitchColorView()
            onPositive { color -> viewModel.saveReminderColor(color) }
        }
    }

    private fun openIntervalPicker() {
        InputSheet().show(requireParentFragment().requireContext()) {
            style(SheetStyle.DIALOG)
            title(this@NewReminderBottomSheet.getString(R.string.time_interval_title))
            with(InputRadioButtons(TIME_UNIT_KEY) {
                label(this@NewReminderBottomSheet.getString(R.string.time_unit_title))
                options(
                    mutableListOf(
                        this@NewReminderBottomSheet.getString(R.string.minutes),
                        this@NewReminderBottomSheet.getString(R.string.hours)
                    )
                )
                selected(0)
            })
            with(InputEditText(INTERVAL_KEY) {
                required()
                this@NewReminderBottomSheet.viewModel.reminder.value?.timeInterval?.let {
                    if (it != 0) defaultValue(it.toString())
                }
                closeIconButton(IconButton(R.drawable.ic_baseline_close_24))
                endIconMode(TextInputLayout.END_ICON_CLEAR_TEXT)
                label(this@NewReminderBottomSheet.getString(R.string.time_interval_pref_title))
                hint(this@NewReminderBottomSheet.getString(R.string.time_hint))
                inputType(InputType.TYPE_CLASS_NUMBER)
            })
            onPositive(this@NewReminderBottomSheet.getString(R.string.action_save)) {
                val timeUnit = it.getInt(TIME_UNIT_KEY)
                val interval = it.getString(INTERVAL_KEY, "0").toInt()
                if (interval != 0) {
                    viewModel.saveTimeInterval(if (timeUnit == 0) interval else interval * 60)
                } else {
                    this@NewReminderBottomSheet.createSnackBar(
                        this@NewReminderBottomSheet.getString(
                            R.string.time_interval_cant_be_zero
                        )
                    )
                }
            }
        }
    }

    private fun openDaysPicker() {
        val alarmDaysString = resources.getStringArray(R.array.alarmDays)
        val savedList = viewModel.reminder.value?.alarmDays
        val options = Utils.getSavedOptions(savedList, alarmDaysString)

        OptionsSheet().show(requireParentFragment().requireContext()) {
            style(SheetStyle.DIALOG)
            title(this@NewReminderBottomSheet.getString(R.string.alarm_days_title))
            multipleChoices(true)
            with(options)
            onPositiveMultiple(this@NewReminderBottomSheet.getString(R.string.action_save)) { indices, _ ->
                viewModel.saveReminderDays(indices.sorted())
            }
        }
    }

    private fun openTimePicker(code: ReminderUi.Item) {
        ClockTimeSheet().show(requireParentFragment().requireContext()) {
            style(SheetStyle.DIALOG)
            title(
                this@NewReminderBottomSheet.getString(
                    if (code is ReminderUi.Item.Start) R.string.start_time_title
                    else R.string.end_time_title
                )
            )
            onPositive { milliseconds: Long, _, _ ->
                if (code is ReminderUi.Item.Start) viewModel.saveStartTime(milliseconds)
                else viewModel.saveEndTime(milliseconds)
            }
        }
    }
}