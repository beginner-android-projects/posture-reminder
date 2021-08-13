package com.puntogris.posture.ui.health

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.puntogris.posture.data.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {


}