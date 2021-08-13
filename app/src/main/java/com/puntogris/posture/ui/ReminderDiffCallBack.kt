package com.puntogris.posture.ui

import androidx.recyclerview.widget.DiffUtil
import com.puntogris.posture.model.Reminder

class ReminderDiffCallBack : DiffUtil.ItemCallback<Reminder>() {

    override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem.equals(newItem)
    }
}