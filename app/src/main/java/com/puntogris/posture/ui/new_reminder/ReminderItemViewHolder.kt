package com.puntogris.posture.ui.new_reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.posture.databinding.ReminderItemVhBinding
import com.puntogris.posture.model.ReminderUi

class ReminderItemViewHolder(private val binding: ReminderItemVhBinding):RecyclerView.ViewHolder(binding.root) {

    fun bind(reminderItem: ReminderUi.Item, clickListener: (ReminderUi)-> Unit){
        binding.reminderItem = reminderItem
        binding.root.setOnClickListener { clickListener(reminderItem) }
        binding.executePendingBindings()
    }

    companion object{
        fun from(parent: ViewGroup): ReminderItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ReminderItemVhBinding.inflate(layoutInflater, parent, false)
            return ReminderItemViewHolder(binding)
        }
    }
}