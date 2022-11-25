package com.capstone.hewankita.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.hewankita.R
import com.capstone.hewankita.data.local.entity.ScheduleAll
import com.capstone.hewankita.databinding.ItemScheduleAllBinding

class ScheduleAllAdapter : RecyclerView.Adapter<ScheduleAllAdapter.ViewHolder>() {
    var listScheduleAll = ArrayList<ScheduleAll>()
        set(list) {
            if (list.size > 0) {
                this.listScheduleAll.clear()
            }
            this.listScheduleAll.addAll(list)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule_all, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listScheduleAll[position])
    }

    override fun getItemCount(): Int = this.listScheduleAll.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemScheduleAllBinding.bind(itemView)
        fun bind(scheduleAll: ScheduleAll) {
            binding.tvItemOutlet.text = scheduleAll.outlet
            binding.tvItemDate.text = scheduleAll.booking_date
            binding.tvItemTime.text = scheduleAll.booking_time
        }
    }
}