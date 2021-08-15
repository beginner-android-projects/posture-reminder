package com.puntogris.posture.ui.portal

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentPortalBinding
import com.puntogris.posture.model.RankingProfile
import com.puntogris.posture.ui.base.BaseFragment
import com.puntogris.posture.ui.main.realmApp
import com.puntogris.posture.ui.rankings.RankingsAdapter
import com.puntogris.posture.utils.showItem
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration

@AndroidEntryPoint
class PortalFragment : BaseFragment<FragmentPortalBinding>(R.layout.fragment_portal) {

    private val viewModel: PortalViewModel by viewModels()

    override fun initializeViews() {
        binding.fragment = this

        val adapter = RankingsAdapter()
        binding.recyclerView.adapter = adapter

        adapter.updateData(viewModel.getTopRankingsUsers())
    }

    fun onSeeGlobalRankingClicked(){
        findNavController().navigate(R.id.action_portalFragment_to_rankingsFragment)
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