package com.puntogris.posture.data.remote

class FirebaseLoginDataSource: FirebaseDataSource() {

    fun logOutFromFirebase(){
        auth.signOut()
    }
}