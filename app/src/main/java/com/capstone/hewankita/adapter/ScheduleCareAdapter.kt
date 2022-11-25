package com.capstone.hewankita.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.hewankita.R
import com.capstone.hewankita.data.local.entity.ScheduleCare
import com.capstone.hewankita.databinding.ItemScheduleCareBinding

class ScheduleCareAdapter : RecyclerView.Adapter<ScheduleCareAdapter.ViewHolder>() {
    var listScheduleCare = ArrayList<ScheduleCare>()
        set(list) {
            if (list.size > 0) {
                this.listScheduleCare.clear()
            }
            this.listScheduleCare.addAll(list)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule_care, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listScheduleCare[position])
    }

    override fun getItemCount(): Int = this.listScheduleCare.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemScheduleCareBinding.bind(itemView)
        fun bind(scheduleCare: ScheduleCare) {
            binding.tvItemOutlet.text = scheduleCare.outlet
            binding.tvItemCheckIn.text = scheduleCare.check_in
            binding.tvItemCheckOut.text = scheduleCare.check_out
            binding.tvItemTimeOfArrival.text = scheduleCare.time_of_arrival
        }
    }
}