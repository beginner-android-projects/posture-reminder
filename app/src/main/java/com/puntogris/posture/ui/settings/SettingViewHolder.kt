package com.puntogris.posture.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.posture.databinding.SettingsItemTextBinding
import com.puntogris.posture.model.ItemData
import com.puntogris.posture.model.SettingItem

class SettingViewHolder(private val binding: SettingsItemTextBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(settingItem: SettingItem, clickListener: (SettingItem) -> Unit){
        binding.item = settingItem
        binding.root.setOnClickListener { clickListener(settingItem) }
        binding.executePendingBindings()
    }

    companion object{
        fun from(parent: ViewGroup): SettingViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SettingsItemTextBinding.inflate(layoutInflater, parent, false)
            return SettingViewHolder(binding)
        }
    }
}