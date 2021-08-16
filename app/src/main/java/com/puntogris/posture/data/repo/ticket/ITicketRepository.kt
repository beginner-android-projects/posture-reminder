package com.puntogris.posture.data.repo.ticket

import com.puntogris.posture.model.RepoResult
import com.puntogris.posture.model.Ticket

interface ITicketRepository {
    suspend fun fillTicketWithUserDataAndSend(ticket: Ticket): RepoResult
}