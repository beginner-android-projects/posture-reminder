package com.puntogris.posture.preferences

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.preference.PreferenceViewHolder
import androidx.preference.SwitchPreference
import com.puntogris.posture.R

class CustomSwitchPref (context: Context, attributeSet: AttributeSet): SwitchPreference(context, attributeSet) {

    @Suppress("DEPRECATION")
    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)
        val title = holder?.itemView?.findViewById<TextView>(android.R.id.title)
        val summary = holder?.itemView?.findViewById<TextView>(android.R.id.summary)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            title?.setTextColor(context.getColor(R.color.white))
            summary?.setTextColor(context.getColor(R.color.grey))
        }else{
            title?.setTextColor(context.resources.getColor(R.color.white))
            summary?.setTextColor(context.resources.getColor(R.color.grey))
        }
    }
}