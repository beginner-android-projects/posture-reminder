package com.puntogris.posture

import android.app.AlarmManager
import android.app.AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import android.os.Build
import com.puntogris.posture.di.HiltBroadcastReceiver
import com.puntogris.posture.utils.Constants.DAILY_ALARM_TRIGGERED
import com.puntogris.posture.utils.Constants.REPEATING_ALARM_TRIGGERED
import com.puntogris.posture.utils.DataStore
import com.puntogris.posture.utils.Utils.minutesSinceMidnight
import com.puntogris.posture.utils.goAsync
import kotlinx.coroutines.*
import javax.inject.Inject

@DelicateCoroutinesApi
class ReminderBroadcast: HiltBroadcastReceiver() {

    @Inject lateinit var alarm: Alarm
    @Inject lateinit var dataStore: DataStore

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        when (intent.action) {
            DAILY_ALARM_TRIGGERED -> goAsync {
//                val reminder = reminderDao.getActiveReminder()
//                if (dayOfTheWeek() in reminder.alarmDays) alarm.startRepeatingAlarm(reminder.timeInterval)
//
//                reminderDao.getActiveReminder().apply {
//                    if (dayOfTheWeek() in alarmDays) alarm.startRepeatingAlarm(timeInterval)
//                }
            }
            REPEATING_ALARM_TRIGGERED -> goAsync {
//                val minutesSinceMidnight = minutesSinceMidnight()
//                reminderDao.getActiveReminder().apply {
//                    if (alarmNotPastMidnight()) {
//                        if (minutesSinceMidnight <= endTime)
//                            deliverNotificationAndSetNewAlarm(context, timeInterval)
//                        else
//                            alarm.cancelRepeatingAlarm()
//                    } else {
//                        if (alarmPastMidnightAndOutOfRange(minutesSinceMidnight))
//                            alarm.cancelRepeatingAlarm()
//                        else
//                            deliverNotificationAndSetNewAlarm(context, timeInterval)
//                    }
//                }
            }
            ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (alarm.canScheduleExactAlarms()){
                        //resume with current alarm
                    }else{
                        alarm.cancelAlarms()
                    }
                }
            }
            ACTION_BOOT_COMPLETED -> {

            }
        }
    }


}