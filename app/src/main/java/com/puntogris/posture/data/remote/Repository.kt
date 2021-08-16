package com.puntogris.posture.data.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.puntogris.posture.model.Ticket
import com.puntogris.posture.model.RepoResult
import com.puntogris.posture.utils.Constants.TICKET_COLLECTION_NAME
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(): IRepository {

    private val firestore = Firebase.firestore

    override suspend fun sendReportToFirestore(ticket: Ticket): RepoResult {
        return try {
            firestore.collection(TICKET_COLLECTION_NAME).document().set(ticket).await()
            RepoResult.Success
        }catch (e:Exception){
            RepoResult.Failure
        }
    }

}
