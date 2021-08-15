package com.puntogris.posture.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.borutsky.neumorphism.NeumorphicFrameLayout
import com.db.williamchart.view.DonutChartView
import com.puntogris.posture.R
import com.puntogris.posture.model.Reminder
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.lang.Exception
import java.util.*

@BindingAdapter("reminderSummaryStatus")
fun TextView.setReminderSummaryStatus(reminder: Reminder?){
    if(reminder != null) text = if (reminder.isActive) context.getString(R.string.alarm_on) else context.getString(
        R.string.alarm_off)
}

@BindingAdapter("reminderStatus")
fun TextView.setReminderStatus(reminder: Reminder?){
    if(reminder != null) text = if (reminder.isActive) context.getString(R.string.stop_alarm) else context.getString(
        R.string.start_alarm)
}

@BindingAdapter("imageFromRes")
fun ImageView.setImageFromRes(@DrawableRes image: Int){
    setImageDrawable(ContextCompat.getDrawable(context, image))
}

@BindingAdapter("imageColor")
fun ImageView.setImageColor(image: Int){
    setBackgroundColor(image)
}

@BindingAdapter("minutesToHourlyTime")
fun TextView.setMinutesToHourlyTime(minutes: Int){
    text = Utils.minutesFromMidnightToHourlyTime(minutes)
}

@BindingAdapter("daysSummary")
fun TextView.setDaysSummary(reminder: Reminder){
    text = reminder.alarmDaysSummary(resources.getStringArray(R.array.alarmDays))
}

@BindingAdapter("reminderColor")
fun ImageView.setReminderColor(color:Int){
    backgroundTintList = try {
        ColorStateList.valueOf(getColor(context, color))
    }catch (e:Exception){
        ColorStateList.valueOf(color)
    }
}

@BindingAdapter("dayMonth")
fun TextView.setDayMonth(date: String){
    val dateNow = LocalDate.parse(date)
    text = dateNow.format(DateTimeFormatter.ofPattern("d MMM"))
}

@BindingAdapter("pagerDay")
fun TextView.setPagerDay(position: Int){
    text = if (position == 0) "Hoy" else "Ayer"
}

@BindingAdapter("accountLevelTitle")
fun TextView.setAccountLevelTitle(exp: Int){
    text = if (exp < 100 )"Nivel 1" else "Nivel ${(exp / 100)}"
}

@BindingAdapter("accountBadgeLevel")
fun TextView.setAccountBadgeLevel(exp: Int){
    text = when(exp / 100){
        0 -> "Espalda al viento"
        1 -> "Espalda de papel"
        2 -> "Espalda de bronce"
        3 -> "Espalda de plata"
        4 -> "Espalda de oro"
        5 -> "Espalda de acero"
        6 -> "Espalda de safiro"
        else -> "Espalda de diamante"
    }
}

@BindingAdapter("expForNextLevel")
fun TextView.setExpForNextLevel(exp: Int){
    val level = if (exp < 100) 1 else (exp / 100)
    text = "+${100 - (exp - (exp / 100) * 100)} para lvl ${level + 1}"
}

@BindingAdapter("expFromTotalLevel")
fun TextView.setExpFromTotalLevel(exp: Int){
    text = "${exp - (exp / 100) * 100} / 100"
}

@BindingAdapter("donutChartProgress")
fun DonutChartView.setDonutChartProgress(exp: Int){
    donutColors = intArrayOf(getColor(context, R.color.colorPrimaryDark))
    animation.duration = 1000
    val data = if (exp < 100) exp else exp - (exp / 100) * 100
    animate(listOf(data.toFloat()))
}

@BindingAdapter("donutLevel")
fun TextView.setDonutLevel(exp: Int){
    text = if (exp < 100 )"Lvl. 1" else "Lvl. ${(exp / 100)}"
}