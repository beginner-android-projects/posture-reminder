package com.puntogris.posture.ui.account

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.puntogris.posture.R
import com.puntogris.posture.databinding.BottomSheetManageRemindersBinding
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.ui.base.BaseBottomSheetFragment
import com.puntogris.posture.utils.createSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManageRemindersBottomSheet: BaseBottomSheetFragment<BottomSheetManageRemindersBinding>(R.layout.bottom_sheet_manage_reminders, false) {

    private val viewModel: ManageRemindersViewModel by viewModels()

    override fun initializeViews() {
        binding.bottomSheet = this

        ManageReminderAdapter(
            requireContext(),
            { onSelectReminder(it) },
            { onEditReminder(it) },
            { onDeleteReminder(it) }
        ).let {
            binding.recyclerView.adapter = it
            subscribeUi(it)
        }
    }

    private fun subscribeUi(adapter: ManageReminderAdapter){
        viewModel.getAllReminders().observe(viewLifecycleOwner){
            adapter.updateList(it)
        }
    }

    private fun onSelectReminder(reminder: Reminder){
        lifecycleScope.launch {
            viewModel.updateCurrentReminder(reminder.id)
            findNavController().navigateUp()
            createSnackBar(getString(R.string.snack_configuration_updated))
        }
    }

    private fun onEditReminder(reminder: Reminder){
        val action = ManageRemindersBottomSheetDirections
            .actionManageRemindersBottomSheetToNewReminderBottomSheet(reminder)
        findNavController().navigate(action)
    }

    private fun onDeleteReminder(reminder: Reminder){
        lifecycleScope.launch {
            viewModel.deleteReminder(reminder)
            Snackbar.make(dialog?.window!!.decorView, getString(R.string.snack_delete_reminder_success), Snackbar.LENGTH_LONG)
                .setAnchorView(binding.floatingActionButton)
                .setAction(getString(R.string.action_undo)){
                    lifecycleScope.launch {
                        viewModel.insertReminder(reminder)
                    }
                }.show()
        }
    }

    fun onNewReminder(){
        findNavController().navigate(R.id.action_manageRemindersBottomSheet_to_newReminderBottomSheet)
    }
}