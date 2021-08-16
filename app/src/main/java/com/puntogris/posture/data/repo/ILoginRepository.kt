package com.puntogris.posture.data.repo

import android.content.Intent
import com.puntogris.posture.model.LoginResult
import kotlinx.coroutines.flow.StateFlow

interface ILoginRepository {
    fun firebaseAuthWithGoogle(idToken: String): StateFlow<LoginResult>
    fun createGoogleSignInIntent(): Intent
    fun signOutUserFromFirebaseAndGoogle()
}