package com.puntogris.posture.ui.health

import androidx.fragment.app.viewModels
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentHealthBinding
import com.puntogris.posture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthFragment : BaseFragment<FragmentHealthBinding>(R.layout.fragment_health) {

    private val viewModel: HealthViewModel by viewModels()

    override fun initializeViews() {


    }
}