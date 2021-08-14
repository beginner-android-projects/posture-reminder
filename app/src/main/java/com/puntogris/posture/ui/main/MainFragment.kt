package com.puntogris.posture.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentMainBinding
import com.puntogris.posture.ui.base.BaseFragment
import com.puntogris.posture.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.jar.Manifest

@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    lateinit var requestPermissionLauncher: ActivityResultLauncher<Intent>
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


        // talvez cambiar de lugar esto, en welcome fragment o algo asi
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { isGranted ->
                //activate alarm
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestPermissionLauncher.launch(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM).also {
                it.data = Uri.parse("package:com.puntogris.posture")
            })
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