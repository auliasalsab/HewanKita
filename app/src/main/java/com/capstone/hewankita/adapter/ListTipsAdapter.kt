package com.capstone.hewankita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.hewankita.databinding.ItemCardTipsBinding
import com.capstone.hewankita.ui.bottom.ui.tips.Tips

class ListTipsAdapter(private var listUser : ArrayList<Tips>) : RecyclerView.Adapter<ListTipsAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemCardTipsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                listUser[holder.adapterPosition]
            )
        }
    }


    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(private var binding: ItemCardTipsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Tips){
            with(itemView) {
                binding.tvItemJudul.text = item.judul
                Glide.with(this)
                    .load(item.photo)
                    .circleCrop()
                    .into(binding.imgItemPhoto)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Tips)
    }
}