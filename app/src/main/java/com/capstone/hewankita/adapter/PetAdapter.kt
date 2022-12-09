package com.capstone.hewankita.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.hewankita.R
import com.capstone.hewankita.data.AllData
import com.capstone.hewankita.databinding.ItemListPetshopBinding
import com.capstone.hewankita.databinding.ItemPetBinding
import com.capstone.hewankita.ui.care.CareActivity
import com.capstone.hewankita.ui.myPet.MyPetActivity
import com.capstone.hewankita.ui.myPet.MyPetActivity.Companion.PET_ID
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class PetAdapter(
    options: FirebaseRecyclerOptions<AllData>,
) : FirebaseRecyclerAdapter<AllData, PetAdapter.MessageViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_pet, parent, false)
        val binding = ItemPetBinding.bind(view)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: AllData) {
        holder.bind(model)
    }

    inner class MessageViewHolder(private val binding: ItemPetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AllData) {
            binding.tvPetName.text = item.PetName
            binding.tvPetType.text = item.Species
            Glide.with(itemView)
                .load(item.PetImg)
                .into(binding.circleImageView)
            itemView.setOnClickListener{
                val intent = Intent(itemView.context, MyPetActivity::class.java)
                intent.putExtra(PET_ID, item.PetId)
                itemView.context.startActivity(intent)
            }
        }
    }
}

/*
class PetAdapter(private val listPetData: ArrayList<AllData>): RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = ItemPetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetViewHolder(binding)
    }

    inner class PetViewHolder(private val binding: ItemPetBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listPet: AllData){
            with(binding){
                tvPetName.text = listPet.PetName
                tvPetType.text = listPet.PetName
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context, MyPetActivity::class.java)
                    intent.putExtra(PET_ID, listPet.PetId)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(listPetData[position])
    }

    override fun getItemCount(): Int = listPetData.size

    companion object {
        const val PET_ID = "Id"
    }
}
 */