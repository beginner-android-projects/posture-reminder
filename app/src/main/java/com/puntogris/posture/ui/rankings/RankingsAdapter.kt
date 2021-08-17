package com.puntogris.posture.ui.rankings

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.posture.model.UserPublicProfile

class RankingsAdapter: RecyclerView.Adapter<RankingsViewHolder>(){

    private var list = listOf<UserPublicProfile>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingsViewHolder {
        return RankingsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RankingsViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount() = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<UserPublicProfile>){
        this.list = list
        println(list.size)
        notifyDataSetChanged()
    }

}