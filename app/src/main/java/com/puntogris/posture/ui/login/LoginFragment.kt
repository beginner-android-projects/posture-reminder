package com.puntogris.posture.ui.login

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentLoginBinding
import com.puntogris.posture.model.LoginResult
import com.puntogris.posture.ui.base.BaseFragment
import com.puntogris.posture.utils.gone
import com.puntogris.posture.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment :BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private lateinit var loginActivityResultLauncher: ActivityResultLauncher<Intent>
    private val viewModel: LoginViewModel by viewModels()

    override fun initializeViews() {
        binding.fragment = this
        registerActivityResultLauncher()
    }

    private fun registerActivityResultLauncher(){
        loginActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK) handleLoginActivityResult(it.data)
            // else mostrar snack diciendo que no se encontro uan cuenta o que hay problemas de conexcion
        }
    }

    private fun handleLoginActivityResult(intent: Intent?){
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            val account = task.getResult(ApiException::class.java)!!
            authUserIntoFirebase(account.idToken!!)
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
        }
    }

    private fun authUserIntoFirebase(idToken: String){
        lifecycleScope.launch {
            viewModel.authUserWithFirebase(idToken).collect {
                handleAuthUserIntoFirebaseResult(it)
            }
        }
    }

    private fun handleAuthUserIntoFirebaseResult(result: LoginResult){
        when (result) {
            is LoginResult.Error -> {
                binding.progressBar.gone()
            }
            LoginResult.InProgress -> {
                binding.progressBar.visible()
            }
            is LoginResult.Success -> {
                val action =
                    LoginFragmentDirections.actionLoginFragmentToSynAccountFragment(result.userPrivateData)
                findNavController().navigate(action)
            }
        }
    }

    fun startLoginWithGoogle() {
        val intent = viewModel.getGoogleSignInIntent()
        loginActivityResultLauncher.launch(intent)
    }
}