package com.puntogris.posture.ui.login

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentLoginBinding
import com.puntogris.posture.ui.base.BaseFragment
import com.puntogris.posture.utils.createSnackBar
import com.puntogris.posture.utils.gone
import com.puntogris.posture.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment :BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private val viewModel: LoginViewModel by viewModels()

    override fun initializeViews() {
        binding.fragment = this

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }
        loginWithEmail()
    }

    private fun loginWithEmail() {
        lifecycleScope.launch {

        }
    }

    fun onStartLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestServerAuthCode("615227546660-7dtf7ucagu76p4sddga1gcc76dkesr06.apps.googleusercontent.com")
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        activityResultLauncher.launch(mGoogleSignInClient.signInIntent)

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.result
            val authorizationCode = account?.serverAuthCode


        } catch (e: ApiException) {
           println(e.localizedMessage)
        }
    }
}