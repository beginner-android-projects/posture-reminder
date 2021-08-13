package com.puntogris.posture.ui.welcome

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentNewUserBinding
import com.puntogris.posture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewUserFragment : BaseFragment<FragmentNewUserBinding>(R.layout.fragment_new_user) {

    private val viewModel: NewUserViewModel by viewModels()

    override fun initializeViews() {
        binding.fragment = this
    }

    fun onContinueButtonClicked(){
        lifecycleScope.launch {
        //    viewModel.updateUsername(binding.username.text.toString())
            findNavController().navigate(R.id.action_newUserFragment_to_batteryOptimizationFragment)
        }
    }
}