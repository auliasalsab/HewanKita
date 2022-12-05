package com.capstone.hewankita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.hewankita.data.AllData
import com.capstone.hewankita.databinding.ItemScheduleCareBinding

class ScheduleCareAdapter(private val listScheduleCare: ArrayList<AllData>) : RecyclerView.Adapter<ScheduleCareAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemScheduleCareBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listScheduleCare[position])
    }

    override fun getItemCount(): Int = listScheduleCare.size

    inner class ViewHolder(private val binding: ItemScheduleCareBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scheduleData: AllData) {
            binding.tvItemOutlet.text = scheduleData.Outlet
            binding.tvItemCheckIn.text = scheduleData.CheckIn
            binding.tvItemCheckOut.text = scheduleData.CheckOut
            binding.tvItemTimeOfArrival.text = scheduleData.TimeOfArrival
        }
    }
}