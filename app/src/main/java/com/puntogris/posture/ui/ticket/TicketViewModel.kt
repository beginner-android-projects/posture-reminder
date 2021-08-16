package com.puntogris.posture.ui.ticket

import androidx.lifecycle.ViewModel
import com.puntogris.posture.data.repo.ticket.TicketRepository
import com.puntogris.posture.model.RepoResult
import com.puntogris.posture.model.Ticket
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
):ViewModel() {

    private var ticket = Ticket()

    suspend fun sendTicket(message: String): RepoResult{
        ticket.message = message
        return ticketRepository.fillTicketWithUserDataAndSend(ticket)
    }

    fun updateTicketType(type: Int){
        ticket.type = if (type == 0) "PROBLEM" else "SUGGESTION"
    }
}