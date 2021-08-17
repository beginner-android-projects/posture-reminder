package com.puntogris.posture.data.remote

import javax.inject.Inject

class FirebaseReminderDataSource @Inject constructor() : FirebaseDataSource() {

    fun getReminderRefWithId(reminderId: String) =
        firestore.collection("users")
            .document(getCurrentUserId())
            .collection("reminders")
            .document(reminderId)

    fun getUserRemindersRef() =
        firestore.collection("users")
            .document(getCurrentUserId())
            .collection("reminders")

}