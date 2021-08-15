package com.puntogris.posture.ui.rankings

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.viewModels
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentRankingsBinding
import com.puntogris.posture.ui.base.BaseFragment
import com.puntogris.posture.utils.showItem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.showItem(R.id.settings)
        menu.showItem(R.id.newReminder)
        super.onCreateOptionsMenu(menu, inflater)
    }
}