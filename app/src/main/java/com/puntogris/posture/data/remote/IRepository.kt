package com.puntogris.posture.data.remote

import com.puntogris.posture.model.Report
import com.puntogris.posture.model.RepoResult

interface IRepository {
    suspend fun sendReportToFirestore(report: Report): RepoResult
}