package com.example.dicodingaplikasi.ui.DetailEvent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.dicodingaplikasi.R
import com.example.dicodingaplikasi.data.local.entity.EventEntity
import com.example.dicodingaplikasi.data.local.room.EventDatabase
import com.example.dicodingaplikasi.data.remote.response.ListEventsItem
import com.example.dicodingaplikasi.databinding.ActivityDetailEventBinding
import com.example.dicodingaplikasi.ui.ViewModelFactory
import com.example.dicodingaplikasi.ui.favorite.FavoriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEventBinding
    private var detailData: ListEventsItem? = null

    companion object{
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_OWNER = "extra_owner"
        const val EXTRA_LOCATION = "extra_location"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_REGISTRANTS = "extra_registrants"
        const val EXTRA_QUOTA = "extra_quota"
        const val EXTRA_BEGIN_TIME = "extra_begin_time"
        const val EXTRA_SUMMARY = "extra_summary"
        const val EXTRA_LINK = "extra_link"
        const val EXTRA_ID = "extra_id"
    }

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = ActivityDetailEventBinding.inflate(layoutInflater)
         setContentView(binding.root)

         val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
         val viewModel: DetailEventViewModel by viewModels { factory }

//       Untuk mendapatkan data dari intent
         val photo = intent.getStringExtra(EXTRA_PHOTO)
         val name = intent.getStringExtra(EXTRA_NAME)
         val owner = intent.getStringExtra(EXTRA_OWNER)
         val location = intent.getStringExtra(EXTRA_LOCATION)
         val description = intent.getStringExtra(EXTRA_DESCRIPTION)
         val registrants = intent.getStringExtra(EXTRA_REGISTRANTS)?.toIntOrNull() ?: 0
         val quota = intent.getStringExtra(EXTRA_QUOTA)?.toIntOrNull() ?: 0
         val beginTime = intent.getStringExtra(EXTRA_BEGIN_TIME)
         val summary = intent.getStringExtra(EXTRA_SUMMARY)
         val link = intent.getStringExtra(EXTRA_LINK)
         val id = intent.getIntExtra(EXTRA_ID, 0)

         Log.d("LOGDEBUG", "id : $id")
         Log.d("LOGDEBUG", " from intent id : ${intent.getStringExtra(EXTRA_ID)}")
         Log.d("LOGDEBUG", " from intent name : ${intent.getStringExtra(EXTRA_NAME)}")

//        Menghitung sisa kuota dengan memeriksan null
         val sisaKuota = quota - registrants

//       Menampilkan data
         binding.tvEventDetail.text = name
         binding.tvOwner.text = owner
         binding.tvLocation.text = location
         binding.tvBegin.text = beginTime
         binding.tvQuota.text = sisaKuota.toString()


         Glide.with(this).load(photo).into(binding.tvEventImage)

         binding.description.text = fromHtml(description ?: "")
         binding.btnRegister.setOnClickListener {
            link?.let{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            } ?: run {
                Toast.makeText(this, "Link tidak tersedia", Toast.LENGTH_SHORT).show()
            }

         }

         viewModel.getEvent(id)

         viewModel.favoriteEvent.observe(this) { event ->
             if(event == null) {
                 // belum dimasukkan sebagai favorite
                 binding.ivFavorite.setImageResource(R.drawable.ic_favorite_border)
             } else {
                 // sudah dimasukkan sebagai favorite
                 binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
             }
         }

//       Favorite
         binding.ivFavorite.setOnClickListener {
             if(viewModel.favoriteEvent.value == null) {
                 //ini belum difavoritkan
                 val event = EventEntity()
                 event.name = name ?: ""
                 event.id = id
                 event.mediaCover = photo ?: ""

                 viewModel.addEvent(event)
             } else {
                 //ini sudah difavoritkan
                 val event = EventEntity()
                 event.name = name ?: ""
                 event.id = id
                 event.mediaCover = photo ?: ""

                 viewModel.deleteEvent(event)
             }

             viewModel.getEvent(id)


         }

     }

    private fun fromHtml(html: String): Spanned {
        return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

//    private fun saveEvent(detailData: ListEventsItem) {
//        val event = EventEntity(
//            id = detailData.id.toString(),
//            name = detailData.name,
//            mediaCover = detailData.mediaCover
//        )
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val eventDao = EventDatabase.getInstance(applicationContext).eventDao()
//                eventDao.insertEvent(event) // Simpan ke database
//                runOnUiThread {
//                    Toast.makeText(this@DetailEventActivity, "Acara ditambahkan ke favorit", Toast.LENGTH_SHORT).show()
//                }
//            } catch (e: Exception) {
//                runOnUiThread {
//                    Toast.makeText(this@DetailEventActivity, "Gagal menambahkan acara ke favorit", Toast.LENGTH_SHORT).show()
//                    e.printStackTrace() // Tambahkan ini untuk melihat error yang terjadi
//                }
//            }
//        }
//    }



}