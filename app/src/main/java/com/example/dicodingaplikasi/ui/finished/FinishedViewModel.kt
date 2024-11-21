package com.example.dicodingaplikasi.ui.finished

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

class FinishedViewModel: ViewModel() {
    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listEvent: LiveData<List<ListEventsItem>> = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _finished = MutableLiveData<List<ListEventsItem>>()
    val finished: LiveData<List<ListEventsItem>> = _finished

    private val _error = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _error

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    companion object{
        private const val TAG = "FinishedViewModel"
        private const val EVENT_ID = "0"
    }

    init {
        listFinishedEvent()
    }

    private fun listFinishedEvent() {
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
                        _listEvent.value = response.body()?.listEvents
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message}")
                }
        })
    }
}