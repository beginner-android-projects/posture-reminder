package com.puntogris.posture.data.remote

import com.puntogris.posture.model.Ticket
import com.puntogris.posture.model.RepoResult

interface IRepository {
    suspend fun sendReportToFirestore(ticket: Ticket): RepoResult
}