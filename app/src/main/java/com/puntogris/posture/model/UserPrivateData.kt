package com.puntogris.posture.model

import com.google.firebase.Timestamp

class UserPrivateData(
    val userId: String,
    val name: String,
    val country: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val creationDate: Timestamp = Timestamp.now()
)