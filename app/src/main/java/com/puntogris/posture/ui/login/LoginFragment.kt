package com.puntogris.posture.ui.login

import android.app.Activity
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
import com.puntogris.posture.model.LoginResult
import com.puntogris.posture.ui.base.BaseFragment
import com.puntogris.posture.ui.main.realmApp
import com.puntogris.posture.utils.createSnackBar
import com.puntogris.posture.utils.gone
import com.puntogris.posture.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import io.realm.mongodb.Credentials
import io.realm.mongodb.auth.GoogleAuthType
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
            viewModel.startLoginWithEmail("tes2@gmail.com", "testtest").collect {
                when(it){
                    is LoginResult.Error -> {
                        binding.progressBar.gone()
                        createSnackBar("Error")
                    }
                    LoginResult.InProgress -> binding.progressBar.visible()
                    LoginResult.Success -> {
                        binding.progressBar.gone()
                        findNavController().navigate(R.id.welcomeFragment)
                    }
                }
            }
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
            val googleCredentials = Credentials.google(authorizationCode, GoogleAuthType.AUTH_CODE)
            realmApp.loginAsync(googleCredentials) { result ->
                if (result.isSuccess) {
//                    val config = SyncConfiguration.Builder(result.get(), "realm_id")
//                        .allowQueriesOnUiThread(true)
//                        .allowWritesOnUiThread(true)
//                        .build()
//
//                    println(realmApp.currentUser()?.profile?.name)
//                    Realm.getInstanceAsync(config, object : Realm.Callback(){
//                        override fun onSuccess(realm: Realm) {
//                            realm.executeTransaction {
//                                val user = it.where(User::class.java).equalTo("uid", result.get().id).findFirst()
//                                if (user == null){
//                                    it.insert(
//                                        User(
//                                            uid = result.get().id,
//                                            name = completedTask.result.displayName
//                                        )
//                                    )
//                                }
//                                findNavController().navigate(R.id.welcomeFragment)
//                            }
//                            realm.close()
//                        }
//                    })

                } else {
                    println(result.error.localizedMessage)
                }
            }
        } catch (e: ApiException) {
           println(e.localizedMessage)
        }
    }
}