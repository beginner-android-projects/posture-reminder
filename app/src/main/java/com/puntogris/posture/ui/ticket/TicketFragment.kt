package com.puntogris.posture.ui.ticket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentTicketBinding
import com.puntogris.posture.ui.base.BaseFragment
import com.puntogris.posture.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketFragment : BaseFragment<FragmentTicketBinding>(R.layout.fragment_ticket) {

    private val viewModel: TicketViewModel by viewModels()

    override fun initializeViews() {
        binding.fragment = this
        //binding.businessText.setAdapter(ArrayAdapter(requireContext(),, items))

    }

    fun onHideKeyboardClicked(){
        hideKeyboard()
    }
}