package com.puntogris.posture.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.borutsky.neumorphism.NeumorphicFrameLayout
import com.ehsanmashhadi.library.presenter.CountryPickerContractor
import com.ehsanmashhadi.library.view.CountryPicker
import com.puntogris.posture.R
import com.puntogris.posture.data.local.UserDao
import com.puntogris.posture.databinding.FragmentMainBinding
import com.puntogris.posture.ui.MainViewModel
import com.puntogris.posture.ui.base.BaseFragment
import com.puntogris.posture.utils.*
import com.puntogris.posture.utils.Utils.minutesFromMidnightToHourlyTime
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
       // setupPager()


       // binding.pandaAnimation.setMinAndMaxFrame(44, 74)

       // binding.pandaAnimation.setMaxFrame(31,61)

        //val asd = binding.pandaAnimation.frame
      //  println(asd)

//        var c = 0
//        binding.circleButton.setOnClickListener {
//            c = if (c == 0) 1 else 0
//            binding.enableTextView.text = if (c == 0) "Activar" else "Desactivar"
//
//          //  binding.circleButton.setShapeType(c)
//        }


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
        if (viewModel.isAppActive()) {
            viewModel.cancelAlarms()
            createSnackBar(getString(R.string.alarms_off_toast))
        }
        else {
            viewModel.startAlarm()
            createSnackBar(getString(
                R.string.notifications_set_toast,
                minutesFromMidnightToHourlyTime(viewModel.reminder.value!!.startTime),
                minutesFromMidnightToHourlyTime(viewModel.reminder.value!!.endTime)
            ))
        }
      //  binding.enableSummaryTextview.playShakeAnimation()
     //   binding.enableTextView.playShakeAnimation()
    }

    override fun onDestroyView() {
        binding.pager.adapter = null
        super.onDestroyView()
    }
}