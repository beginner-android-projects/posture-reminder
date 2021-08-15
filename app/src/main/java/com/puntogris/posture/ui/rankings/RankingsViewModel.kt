package com.puntogris.posture.ui.rankings

import androidx.lifecycle.ViewModel
import com.puntogris.posture.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RankingsViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){

    fun getRankingsData() = repository.getGlobalRankings()
}