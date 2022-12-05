package com.capstone.hewankita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.hewankita.data.AllData
import com.capstone.hewankita.databinding.ItemScheduleAllBinding

class ScheduleAllAdapter(private val listScheduleAll: ArrayList<AllData>) : RecyclerView.Adapter<ScheduleAllAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemScheduleAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listScheduleAll[position])
    }

    override fun getItemCount(): Int = listScheduleAll.size

    inner class ViewHolder(private val binding: ItemScheduleAllBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scheduleData: AllData) {
            binding.tvItemOutlet.text = scheduleData.Outlet
            binding.tvItemDate.text = scheduleData.Date
            binding.tvItemTime.text = scheduleData.Time
        }
    }
}