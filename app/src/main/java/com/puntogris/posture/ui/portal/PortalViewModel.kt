package com.puntogris.posture.ui.portal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.puntogris.posture.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PortalViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    fun getTopRankingsUsers() = repository.getTopThreeExpRankings()

}