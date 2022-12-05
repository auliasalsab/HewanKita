package com.capstone.hewankita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.hewankita.data.AllData
import com.capstone.hewankita.databinding.ItemCardTipsBinding
import com.capstone.hewankita.databinding.ItemScheduleAllBinding

class ListTipsAdapter(private var listTips : ArrayList<AllData>) : RecyclerView.Adapter<ListTipsAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemCardTipsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.tvItemJudul.text = listTips[position].Judul
        Glide.with(holder.itemView.context)
            .load(listTips[position].TipsImage)
            .into(holder.binding.imgItemPhoto)
    }

    override fun getItemCount(): Int = listTips.size

    class ListViewHolder(val binding: ItemCardTipsBinding) : RecyclerView.ViewHolder(binding.root)
}
