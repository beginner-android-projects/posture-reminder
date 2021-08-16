package com.puntogris.posture.data.repo.login

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.puntogris.posture.BuildConfig
import com.puntogris.posture.model.LoginResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    @ApplicationContext private val context: Context
): ILoginRepository {

    private val auth = FirebaseAuth.getInstance()

    private fun getGoogleSignInClient(): GoogleSignInClient{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

    override fun firebaseAuthWithGoogle(idToken: String): StateFlow<LoginResult> {
        val result = MutableStateFlow<LoginResult>(LoginResult.InProgress)
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                val user = auth.currentUser

                result.value = LoginResult.Success }
            .addOnFailureListener { result.value = LoginResult.Error(it.localizedMessage) }

        return result
    }

    override fun createGoogleSignInIntent(): Intent{
        return getGoogleSignInClient().signInIntent
    }

    override fun signOutUserFromFirebaseAndGoogle() {
        auth.signOut()
        getGoogleSignInClient().signOut()
    }
}