package com.example.dicodingaplikasi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingaplikasi.R
import com.example.dicodingaplikasi.data.remote.response.ListEventsItem
import com.example.dicodingaplikasi.databinding.ItemEventListBinding

class EventAdapter(private val onItemClick: (ListEventsItem) -> Unit): ListAdapter<ListEventsItem, EventAdapter.MyViewHolder>(DIFF_CALLBACK){
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEventListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)

//        val ivFavorite = holder.binding.iv_favorite
    }

    class MyViewHolder(val binding: ItemEventListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvName.text = event.name
            binding.tvOwner.text = event.ownerName
            binding.tvLocation.text = event.cityName
            Glide.with(binding.root.context).load(event.imageLogo).into(binding.ivThumbnail)
        }
    }

}
