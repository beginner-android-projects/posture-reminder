package com.puntogris.posture.ui.health

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentHealthBinding
import com.puntogris.posture.di.realmApp
import com.puntogris.posture.model.User
import com.puntogris.posture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.kotlin.toFlow
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

@AndroidEntryPoint
class HealthFragment : BaseFragment<FragmentHealthBinding>(R.layout.fragment_health) {

    private val viewModel: HealthViewModel by viewModels()

    override fun initializeViews() {


    }
}