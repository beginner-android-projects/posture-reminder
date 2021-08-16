package com.puntogris.posture.ui.ticket

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.puntogris.posture.R
import com.puntogris.posture.databinding.BottomSheetTicketBinding
import com.puntogris.posture.model.RepoResult
import com.puntogris.posture.ui.base.BaseBottomSheetFragment
import com.puntogris.posture.utils.hideKeyboard
import com.puntogris.posture.utils.showSnackBarInBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TicketBottomSheet : BaseBottomSheetFragment<BottomSheetTicketBinding>(R.layout.bottom_sheet_ticket, true) {

    private val viewModel: TicketViewModel by viewModels()

    override fun initializeViews() {
        binding.bottomSheet = this
        setupTicketTypeAdapter()
    }

    private fun setupTicketTypeAdapter(){
        val items = resources.getStringArray(R.array.ticket_types)
        binding.ticketType.apply {
            setAdapter(ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1, items))
            setOnItemClickListener { _, _, i, _ ->
                viewModel.updateTicketType(i)
            }
        }
    }

    fun onSendTicketClicked(){
        val message = binding.messageText.text.toString()
        lifecycleScope.launch {
            when(viewModel.sendTicket(message)){
                RepoResult.Failure -> showSnackBarInBottomSheet(R.string.snack_send_ticket_error)
                RepoResult.Success -> showSnackBarInBottomSheet(R.string.snack_send_ticket_success)
            }
            dismiss()
        }
    }

    fun onHideKeyboardClicked(){
        hideKeyboard()
    }
}