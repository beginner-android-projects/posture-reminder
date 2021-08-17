package com.puntogris.posture.data.remote

import javax.inject.Inject

class FirebaseSyncDataSource @Inject constructor(): FirebaseDataSource() {

    fun getBatch() = firestore.batch()

    fun getUserPrivateDataRef() =
        firestore
        .collection("users")
        .document(getCurrentUserId())

    fun getUserPublicProfileRef() =
        firestore
            .collection("users")
            .document(getCurrentUserId())
            .collection("publicProfile")
            .document("profile")

    fun getUserRemindersQuery() =
        firestore
            .collection("users")
            .document(getCurrentUserId())
            .collection("reminders")
            .limit(10)

}