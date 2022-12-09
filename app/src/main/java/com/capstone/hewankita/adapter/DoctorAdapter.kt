package com.capstone.hewankita.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.hewankita.R
import com.capstone.hewankita.data.AllData
import com.capstone.hewankita.databinding.ItemListPetshopBinding
import com.capstone.hewankita.ui.doctor.DoctorActivity
import com.capstone.hewankita.ui.doctor.DoctorFragment

class DoctorAdapter (private val listOutlet: ArrayList<AllData>): RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val binding = ItemListPetshopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorViewHolder(binding)
    }

    inner class DoctorViewHolder(private val binding: ItemListPetshopBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listPetShop: AllData){
            with(binding){
                tvOutletName.text = listPetShop.Username
                tvOutletAddress.text = listPetShop.Address
                if (listPetShop.ProfileImage.isNullOrEmpty()){
                    imgOutlet.setImageResource(R.drawable.logo)
                }else {
                    Glide.with(itemView.context)
                        .load(listPetShop.ProfileImage)
                        .into(imgOutlet)
                }
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context,DoctorActivity::class.java)
                    intent.putExtra(OUTLET_ID, listPetShop.Key)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.bind(listOutlet[position])
    }

    override fun getItemCount(): Int = listOutlet.size

    companion object {
        const val OUTLET_ID = "Id"
    }
}