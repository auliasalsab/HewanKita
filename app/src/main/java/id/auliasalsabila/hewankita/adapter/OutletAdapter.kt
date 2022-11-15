package id.auliasalsabila.hewankita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.auliasalsabila.hewankita.data.Outlet
import id.auliasalsabila.hewankita.databinding.ItemRowOutletBinding

class OutletAdapter(private val listOutlet: ArrayList<Outlet>) : RecyclerView.Adapter<OutletAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowOutletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOutlet[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                listOutlet[holder.adapterPosition]
            )
        }
    }

    override fun getItemCount(): Int = listOutlet.size

    inner class ViewHolder(private var binding: ItemRowOutletBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Outlet){
            with(itemView) {
                binding.tvNameOutlet.text = item.nameOutlet
                binding.tvAddress.text = item.address
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Outlet)
    }
}