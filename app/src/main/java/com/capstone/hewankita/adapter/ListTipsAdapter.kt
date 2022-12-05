package com.capstone.hewankita.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.hewankita.data.AllData
import com.capstone.hewankita.databinding.ItemCardTipsBinding

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
        holder.itemView.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(listTips[position].TipsUrl)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listTips.size

    class ListViewHolder(val binding: ItemCardTipsBinding) : RecyclerView.ViewHolder(binding.root)
}
