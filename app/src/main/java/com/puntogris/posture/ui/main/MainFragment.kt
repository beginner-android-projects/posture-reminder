package com.puntogris.posture.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentMainBinding
import com.puntogris.posture.di.realmApp
import com.puntogris.posture.model.User
import com.puntogris.posture.ui.base.BaseFragment
import com.puntogris.posture.utils.*
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.kotlin.toFlow
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun initializeViews() {
        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
            it.fragment = this
            it.pandaAnimation.setPadding(0, 0, -100, -110)
        }
        setupPager()

        var c = 0
        binding.circleButton.setOnClickListener {
            c = if (c == 0) 1 else 0
            binding.enableTextView.text = if (c == 0) "Activar" else "Desactivar"

          //  binding.circleButton.setShapeType(c)
        }

    }

    private fun setupPager(){
        val pagerAdapter = DayHistoryMainPagerAdapter()
        subscribeUi(pagerAdapter)
        binding.pager.apply {
            adapter = pagerAdapter
            binding.dotsIndicator.setViewPager2(this)
            setPageFadeTransformer()
        }
    }

    private fun subscribeUi(adapter: DayHistoryMainPagerAdapter){
        viewModel.getLastTwoDaysHistory().observe(viewLifecycleOwner){
            adapter.updateList(it)
        }
    }

    fun onCloseCardButtonClicked(){
       // binding.announcementCard.gone()
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

    fun onScreenClicked(){
       // if (viewModel.isAppActive()) {
     //       viewModel.cancelAlarms()
     //       createSnackBar(getString(R.string.alarms_off_toast))
    //    }
   //     else {
     //       viewModel.startAlarm()
   //         createSnackBar(getString(
        //        R.string.notifications_set_toast,
       //         minutesFromMidnightToHourlyTime(viewModel.reminder.value!!.startTime),
      //          minutesFromMidnightToHourlyTime(viewModel.reminder.value!!.endTime)
     //       ))
    //    }
      //  binding.enableSummaryTextview.playShakeAnimation()
     //   binding.enableTextView.playShakeAnimation()
    }

    override fun onDestroyView() {
        binding.pager.adapter = null
        super.onDestroyView()
    }
}