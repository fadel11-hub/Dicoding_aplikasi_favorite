package com.example.dicodingaplikasi.ui.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingaplikasi.data.remote.response.ListEventsItem
import com.example.dicodingaplikasi.databinding.ItemEventListBinding

//Kelas UpcomingAdapter dan MyViewHolder mengenkapsulasi data dan perilaku yang terkait dengan daftar event dan tampilan item. Data (seperti ListEventsItem) dan metode (seperti bind) disatukan dalam satu unit.
class UpcomingAdapter(private val onItemClick: (ListEventsItem) -> Unit) : ListAdapter<ListEventsItem, UpcomingAdapter.MyViewHolder>(DIFF_CALLBACK) {
//UpcomingAdapter mewarisi dari ListAdapter, yang merupakan bagian dari library Android. Ini menunjukkan penggunaan pewarisan, di mana UpcomingAdapter dapat menggunakan metode dan properti dari ListAdapter diatas
    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                // Memeriksa apakah dua item adalah objek yang sama berdasarkan ID atau atribut unik lainnya.
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                // Memeriksa apakah konten dua item sama
                return oldItem == newItem
            }
        }
    }

    /**
     * ViewHolder untuk item dalam daftar upcoming events.
     * Menyediakan fungsi untuk mengikat data ke tampilan item.
     *
     * @param binding Objek binding untuk layout item (`ItemEventListBinding`).
     */
    class MyViewHolder(private val binding: ItemEventListBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Mengikat data `ListEventsItem` ke tampilan dalam layout item.
         *
         * @param item Objek `ListEventsItem` yang berisi data event.
         * @param onItemClick Fungsi yang dipanggil ketika item diklik.
         */
        fun bind(item: ListEventsItem, onItemClick: (ListEventsItem) -> Unit) {
            // Menampilkan data event pada tampilan
            binding.tvName.text = item.name
            binding.tvOwner.text = item.ownerName
            binding.tvLocation.text = item.cityName
            Glide.with(binding.root.context).load(item.imageLogo).into(binding.ivThumbnail)

            // Menambahkan tindakan klik pada root layout item untuk membuka detail event
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

//    Metode onBindViewHolder dan onCreateViewHolder adalah contoh polymorphism, di mana metode yang sama didefinisikan dalam kelas induk (ListAdapter) dan diimplementasikan di kelas turunan (UpcomingAdapter). Ini memungkinkan UpcomingAdapter untuk memberikan perilaku spesifiknya sendiri.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Menginflate layout item `ItemEventListBinding` dan menginisialisasi ViewHolder
        val binding = ItemEventListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Mengikat data event ke ViewHolder pada posisi tertentu
        val event = getItem(position)
        holder.bind(event, onItemClick)
    }
}
