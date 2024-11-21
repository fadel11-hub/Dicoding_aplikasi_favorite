package com.example.dicodingaplikasi.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingaplikasi.R
import com.example.dicodingaplikasi.data.local.entity.EventEntity
import com.example.dicodingaplikasi.data.remote.response.ListEventsItem
import com.example.dicodingaplikasi.databinding.ItemEventListBinding

class FavoriteAdapter(private val onFavoriteClick: (EventEntity) -> Unit) :
    ListAdapter<ListEventsItem, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEventListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event) { selectedItem ->
            onFavoriteClick(
                EventEntity(
                    id = selectedItem.id,
                    name = selectedItem.name,
                    mediaCover = selectedItem.imageLogo,
                    summary = selectedItem.summary,
                    description = selectedItem.description,
                    ownerName = selectedItem.ownerName,
                    cityName = selectedItem.cityName,
                    quota = selectedItem.quota,
                    link = selectedItem.link,
                    beginTime = selectedItem.beginTime,
                    endTime = selectedItem.endTime,
                    category = selectedItem.category,
                    registrants = selectedItem.registrants
                )
            )
        }
    }

    class MyViewHolder(val binding: ItemEventListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListEventsItem, onItemClick: (ListEventsItem) -> Unit) {
            binding.tvName.text = item.name
            binding.tvOwner.text = item.ownerName
            binding.tvLocation.text = item.cityName
            Glide.with(binding.root.context).load(item.imageLogo).into(binding.ivThumbnail)

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
