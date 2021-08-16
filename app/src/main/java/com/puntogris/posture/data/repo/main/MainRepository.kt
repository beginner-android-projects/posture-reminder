package com.puntogris.posture.data.repo.main

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class MainRepository @Inject constructor(): IMainRepository{

    private val auth = FirebaseAuth.getInstance()

    override fun isUserLoggedIn() = auth.currentUser != null
}