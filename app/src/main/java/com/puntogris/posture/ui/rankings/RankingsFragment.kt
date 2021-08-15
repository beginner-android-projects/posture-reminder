package com.puntogris.posture.ui.rankings

import androidx.fragment.app.viewModels
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentRankingsBinding
import com.puntogris.posture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingsFragment : BaseFragment<FragmentRankingsBinding>(R.layout.fragment_rankings) {

    private val viewModel: RankingsViewModel by viewModels()

    override fun initializeViews() {
        RankingsAdapter().let {
            binding.recyclerView.adapter = it
            it.updateData(viewModel.getRankingsData())
        }
    }
}