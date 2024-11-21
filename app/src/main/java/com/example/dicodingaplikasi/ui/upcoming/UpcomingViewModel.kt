package com.example.dicodingaplikasi.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingaplikasi.data.remote.response.EventResponse
import com.example.dicodingaplikasi.data.remote.response.ListEventsItem
import com.example.dicodingaplikasi.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel untuk menampilkan data event yang akan datang (upcoming events).
 *
 * UpcomingViewModel bertanggung jawab untuk mengelola data event yang akan datang
 * dan menyediakan data tersebut dalam bentuk LiveData untuk diobservasi oleh komponen UI.
 */
class UpcomingViewModel : ViewModel() {

    // LiveData untuk menampung daftar event yang didapat dari API
    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listEvent: LiveData<List<ListEventsItem>> = _listEvent

    // LiveData untuk menandakan apakah data sedang dimuat
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData untuk menampung event upcoming khusus (bila ada)
    private val _upcoming = MutableLiveData<List<ListEventsItem>>()
    val upcoming: LiveData<List<ListEventsItem>> = _upcoming

    // LiveData untuk menandakan apakah terjadi error
    private val _error = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _error

    // LiveData untuk menampung pesan error
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    companion object {
        private const val TAG = "UpcomingViewModel"
        private const val EVENT_ID = "1" // ID khusus untuk mendeteksi event upcoming
    }

    init {
        listUpcomingEvent()
    }

    /**
     * Mengambil daftar event yang akan datang dari API menggunakan Retrofit.
     *
     * Fungsi ini mengatur [_isLoading] ke `true` saat permintaan API dimulai,
     * kemudian setelah mendapat respons (baik berhasil maupun gagal), mengatur [_isLoading]
     * ke `false`. Jika permintaan berhasil, data disimpan di [_listEvent].
     * Jika gagal, pesan error dicatat di log.
     */
    private fun listUpcomingEvent() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUpComing(EVENT_ID)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Menyimpan daftar event ke LiveData
                        _listEvent.value = response.body()?.listEvents
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                // Menangani kegagalan dengan mencatat log error
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
