package com.puntogris.posture.utils

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.PowerManager
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.puntogris.posture.R
import com.puntogris.posture.di.realmApp
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.utils.Utils.minutesFromMidnightToHourlyTime
import io.realm.mongodb.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import kotlin.math.abs

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun Int.getHours() = this / 60

fun Int.getMinutes() = this % 60

fun Long.millisToMinutes() = (this / 1000 / 60).toInt()

fun Int.minutesToMillis() = this * 1000 * 60

fun Fragment.createSnackBar(message: String){
    val snackLayout = this.requireActivity().findViewById<View>(android.R.id.content)
    Snackbar.make(snackLayout, message, Snackbar.LENGTH_LONG).show()
}

fun AppCompatActivity.getNavController() = getNavHostFragment().navController

fun AppCompatActivity.getNavHostFragment() =
    (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)

fun PreferenceFragmentCompat.preference(key:String ) = findPreference<Preference>(key)

fun View.playShakeAnimation(){
    ObjectAnimator
        .ofFloat(this,"translationX", 0f, 25f, -25f, 25f, -25f,15f, -15f, 6f, -6f, 0f)
        .setDuration(Constants.SHAKE_ANIMATION_DURATION)
        .start()
}

fun Activity.isDarkThemeOn() =
    (resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES)

fun Fragment.isDarkThemeOn() =
    (resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES)

fun Menu.showItem(item: Int){
    findItem(item).isVisible = true
}

fun Fragment.isIgnoringBatteryOptimizations(): Boolean{
    val pm = requireContext().getSystemService(PowerManager::class.java)
    return (pm.isIgnoringBatteryOptimizations(requireActivity().packageName))
}

fun <T> MutableLiveData<T>.setField(transform: T.() -> Unit) { this.value = this.value?.apply(transform) }

fun LocalDate.getEpochTimestamp() =
    atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000

fun ViewPager2.setPageFadeTransformer(){
    setPageTransformer { page, position ->
        page.alpha = when {
            position <= -1.0F || position >= 1.0F -> 0.0F
            position == 0.0F -> 1.0F
            else -> 1.0F - abs(position)
        }
    }
}


fun String.capitalizeFirstChar() =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

fun LocalDate.getDayStringFormatted() = format(DateTimeFormatter.ofPattern("EEE ")).replace(".","").capitalizeFirstChar()

fun Fragment.openInBrowser(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    } catch (e: Throwable) {
        //snack e.message
    }
}

@DelicateCoroutinesApi
fun BroadcastReceiver.goAsync(
    coroutineScope: CoroutineScope = GlobalScope,
    block: suspend () -> Unit
) {
    val result = goAsync()
    coroutineScope.launch {
        try {
            block()
        } finally {
            // Always call finish(), even if the coroutineScope was cancelled
            result.finish()
        }
    }
}

fun App.userId() = currentUser()?.id.toString()
