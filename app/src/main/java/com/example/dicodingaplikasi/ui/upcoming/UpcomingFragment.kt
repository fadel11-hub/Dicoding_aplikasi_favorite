package com.example.dicodingaplikasi.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingaplikasi.R
import com.example.dicodingaplikasi.data.remote.response.ListEventsItem
import com.example.dicodingaplikasi.databinding.FragmentUpcomingBinding
import com.example.dicodingaplikasi.ui.DetailEvent.DetailEventActivity

/**
 * Fragment yang menampilkan daftar event yang akan datang.
 *
 * `UpcomingFragment` mengelola antarmuka daftar event yang diperoleh dari API,
 * menggunakan `UpcomingViewModel` untuk mendapatkan data.
 */
class UpcomingFragment : Fragment() {

    // Binding untuk mengakses view di layout
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding

    // Menginisialisasi ViewModel untuk upcoming events
    private val upcomingViewModel by viewModels<UpcomingViewModel>()

    // Adapter untuk daftar upcoming events
    private lateinit var adapter: UpcomingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout dan inisialisasi binding
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menginisialisasi adapter dengan fungsi klik untuk membuka detail event
        adapter = UpcomingAdapter { event -> openEventDetail(event) }
        binding?.rvEvent?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvEvent?.adapter = adapter

        // Observasi LiveData isLoading dari ViewModel untuk menampilkan progress bar
        upcomingViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        // Observasi LiveData listEvent dari ViewModel untuk mengupdate daftar event di adapter
        upcomingViewModel.listEvent.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events)
        }
    }

    /**
     * Membuka detail event menggunakan `DetailEventActivity`.
     *
     * Fungsi ini menerima sebuah objek `ListEventsItem` dan mengirim data event
     * melalui intent ke `DetailEventActivity` untuk ditampilkan secara rinci.
     *
     * @param event Objek `ListEventsItem` yang berisi detail event.
     */
    private fun openEventDetail(event: ListEventsItem) {
        val intent = Intent(requireContext(), DetailEventActivity::class.java).apply {
            putExtra(DetailEventActivity.EXTRA_PHOTO, event.imageLogo)
            putExtra(DetailEventActivity.EXTRA_NAME, event.name)
            putExtra(DetailEventActivity.EXTRA_OWNER, event.ownerName)
            putExtra(DetailEventActivity.EXTRA_LOCATION, event.cityName)
            putExtra(DetailEventActivity.EXTRA_DESCRIPTION, event.description)
            putExtra(DetailEventActivity.EXTRA_BEGIN_TIME, event.beginTime)
            putExtra(DetailEventActivity.EXTRA_REGISTRANTS, event.registrants)
            putExtra(DetailEventActivity.EXTRA_QUOTA, event.quota)
            putExtra(DetailEventActivity.EXTRA_SUMMARY, event.summary)
            putExtra(DetailEventActivity.EXTRA_LINK, event.link)
            putExtra(DetailEventActivity.EXTRA_ID, event.id)
        }

        Log.d("LOGDEBUG", "this is id in upcoming: ${event.id}")
        startActivity(intent)
    }

    /**
     * Menampilkan atau menyembunyikan progress bar berdasarkan nilai `isLoading`.
     *
     * @param isLoading Boolean yang menandakan apakah loading sedang berlangsung.
     */
    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Menghapus referensi binding saat view dihancurkan untuk mencegah memory leaks
    }
}
