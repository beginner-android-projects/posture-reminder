package com.puntogris.posture.ui.portal

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentPortalBinding
import com.puntogris.posture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PortalFragment : BaseFragment<FragmentPortalBinding>(R.layout.fragment_portal) {

    private val viewModel: PortalViewModel by viewModels()

    override fun initializeViews() {
        binding.fragment = this
    }

    fun navigateToGlobalRanking(){
        findNavController().navigate(R.id.action_portalFragment_to_rankingsFragment)
    }
}