package com.puntogris.posture.ui.sync

import androidx.fragment.app.viewModels
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentSynAccountBinding
import com.puntogris.posture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SynAccountFragment : BaseFragment<FragmentSynAccountBinding>(R.layout.fragment_syn_account) {

    private val viewModel: SyncAccountViewModel by viewModels()

    override fun initializeViews() {

    }
}