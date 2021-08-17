package com.puntogris.posture.data.repo.sync

import com.puntogris.posture.model.UserPrivateData
import com.puntogris.posture.model.RepoResult

interface ISyncRepository {
    suspend fun syncFirestoreAccountWithRoom(userPrivateData: UserPrivateData): RepoResult
}