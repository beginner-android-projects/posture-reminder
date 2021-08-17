package com.puntogris.posture.ui.sync

import androidx.lifecycle.ViewModel
import com.puntogris.posture.data.repo.sync.SyncRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SyncAccountViewModel @Inject constructor(
    private val syncRepository: SyncRepository
):ViewModel() {
}